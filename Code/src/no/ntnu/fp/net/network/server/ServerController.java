package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jdom.adapters.XML4JDOMAdapter;

import no.ntnu.fp.storage.db.DatabaseController;
import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.XmlHandler;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.net.network.Request;
import no.ntnu.fp.net.network.Request.Method;
import no.ntnu.fp.net.network.Tuple;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

//TODO: Communicate with the db
public class ServerController {
	// fields
	private Map<String, Socket> connectedClients;
	private ArrayList<String> participants;
	private DatabaseController databaseController;
	private XmlHandler xmlHandler;
	private Queue<Tuple<Socket, Object>> inQueue;
	private Map<String, ArrayList<String>> views;

	// Constructor
	public ServerController(Map<String, Socket> clients,
			Queue<Tuple<Socket, Object>> inQueue) {
		databaseController = new DatabaseController();
		connectedClients = clients;
		this.inQueue = inQueue;
		views = new HashMap<String, ArrayList<String>>();
	}

	/**
	 * @author bj0rn Helper method for sending over objects over TCP
	 * **/
	private void send(Socket socket, Object data) {
		try {
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// TODO: Call this method when the server starts
	private void getSubscribers() {
		try {
			List<Tuple<String, String>> list = databaseController.getSubscribers();
			for (Tuple<String, String> t : list) {
				if (views.containsKey(t.y)) {
					views.get(t.x).add(t.x);
				} else {
					ArrayList<String> l = new ArrayList<String>();
					l.add(t.x);
					views.put(t.y, l);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author bj0rn Authenticate the user by checking the database. Sends a
	 *         login succeded if the login credentials are correct, or login
	 *         failed otherwise.
	 * **/
	public void authenticate(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();
			String username = auth.getUsername();
			String password = auth.getPassword();
			System.out.println("Username: " + username);
			System.out.println("Password: " + password);
			// Check username and password againts the database
			if (databaseController.authenticate(username, password)) {
				connectedClients.put(username, data.x);
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_SUCCEDED);
				send(data.x, response);
				System.out.println("Login completed");
			} else {
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	/**
	 * @author bj0rn Get all user from the database. If the user who requested
	 *         the users is authenticated, send the users and a
	 *         GET_USERS_RESPONSE Flag, otherwise send LOGIN_FAILED Flag
	 * 
	 * **/
	public void getUsers(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();

			System.out.println("GetUser user: " + auth.getUsername());
			if (connectedClients.containsKey(auth.getUsername())) {
				List<User> users = databaseController.getListOfUsers();
				Request response = new Request(null, (Object) users);
				response.setMethod(Request.Method.GET_USERS_RESPONSE);
				// send the data to the client
				send(data.x, response);
			} else {
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void markViewed(String requestFrom, String viewUser)
			throws SQLException {
		// a user can view more than on calendar
		if (!requestFrom.equals(viewUser)) {
			if (views.containsKey(viewUser)) {
				ArrayList<String> list = views.get(viewUser);
				list.add(requestFrom);
			} else {
				ArrayList<String> list = new ArrayList<String>();
				list.add(requestFrom);
				views.put(viewUser, list);
				System.out.println("request From (Value): " + requestFrom);
				System.out.println("view user (key)" + viewUser);
			}

			databaseController.subscribeToCalendar(requestFrom, viewUser);
		}
	}

	private void sendChangesToViewers(String username, Object data) {
		if (views.containsKey(username)) {
			ArrayList<String> list = views.get(username);
			for (String s : list) {
				if (connectedClients.containsKey(s)) {
					Socket sockfd = connectedClients.get(s);
					Request response = new Request(null, data);
					if (data instanceof Meeting) {
						response.setMethod(Method.CHANGE_MEETING_NOTFICATION);
					} else if (data instanceof Appointment) {
						response.setMethod(Method.CHANGE_APPOINTMENT_NOTIFICATION);
					}
					send(sockfd, response);
				}
			}
		}
	}

	/**
	 * @author bj0rn Get full user from the database If the user, who requested
	 *         the message is authenticated, send the User and a
	 *         GET_FULL_USERS_RESPONSE message, send a LOGIN_FAILED message
	 *         otherwise
	 * 
	 * **/
	// TODO: Implements
	public void getFullUser(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();
			String username = (String) request.getObject();

			if (connectedClients.containsKey(auth.getUsername())) {

				// TODO:test
				markViewed(auth.getUsername(), username);

				User user = databaseController.getFullUser(username);
				Request response = new Request(null, user);
				System.out.println("User " + user.getName());
				response.setMethod(Request.Method.GET_FULL_USER_RESPONSE);
				send(data.x, response);
			} else {
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, XmlHandler.loginUnsucessful());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author bj0rn Save meeting Save received meeting object, if the user whom
	 *         sent the object is authenticated.
	 * 
	 * 
	 * **/
	public void saveMeeting(Tuple<Socket, Object> data) {
		try {

			Request request = (Request) data.y;
			Meeting meeting = (Meeting) request.getObject();
			String username = meeting.getOwner().getUsername();
			System.out.println("Owner: " + username);

			if (connectedClients.containsKey(username)) {
				Integer id = databaseController.saveMeeting(meeting);
				meeting.setID(id);
				// send key to owner
				Request response = new Request(null, id);
				response.setMethod(Request.Method.SAVE_MEETING_RESPONSE);
				send(data.x, response);
				System.out.println("Send data to connected clients");

				// TODO: Does this work
				sendChangesToViewers(username, meeting);

				// also send message to available clients
				System.out.println("Send data to connected clients");
				Set<User> participants = meeting.getParticipants();
				for (User u : participants) {
					String user = u.getUsername();
					System.out.println("Participant: " + user);
					if (connectedClients.containsKey(user)) {
						System.out.println("Works");
						Socket sockfd = connectedClients.get(username);
						Request r = new Request(null, meeting);
						r.setMethod(Request.Method.MEETING_NOTIFICATION);
						send(sockfd, r);
					} else {
						// do some stuff in the db ?
						System.out.println("Sorry the client is not connected");
					}
				}
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	/**
	 * @author bj0rn
	 * 
	 * 
	 * **/
	public void saveAppointment(Tuple<Socket, Object> data) {
		// Got an appointment object
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();
			Appointment a = (Appointment) request.getObject();
			System.out.println("user: " + auth.getUsername());
			if (connectedClients.containsKey(auth.getUsername())) {
				Integer id = databaseController.saveAppointment(a);
				a.setID(id);
				Request response = new Request(null, id);
				response.setMethod(Request.Method.SAVE_APPOINTMENT_RESPONSE);
				send(data.x, response);

				// TODO: does this work
				sendChangesToViewers(auth.getUsername(), a);

			} else {
				// Not authenticated
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	// public void savePlace(Tuple <Socket, Object> data){
	// try {
	// Place place = (Place)data.y;
	//
	//
	//
	// }catch(SQLException sq){
	// sq.printStackTrace();
	// }
	// }

	public void dispatchMeetingReply(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();
			if (connectedClients.containsKey(auth.getUsername())) {
				String xml = (String) request.getObject();
				ArrayList<String> dataValues = (ArrayList<String>) XmlHandler
						.dispatchMeetingReplyFromXml(xml);
				String username = dataValues.get(0);
				System.out.println("Userid " + username);
				String meetingId = dataValues.get(1);
				System.out.println("MeetingId " + meetingId);
				String state = dataValues.get(2);
				System.out.println("State " + state);
				// Check with db
				if (databaseController.updateMeetingState(username, meetingId,
						State.getState(state))) {
					// send success
					Request response = new Request(null, null);
					response.setMethod(Method.SAVE_APPOINTMENT_RESPONSE);
					send(data.x, response);
				} else {
					Request response = new Request(null, null);
					response.setMethod(Method.LOGIN_FAILED);
					send(data.x, response);
				}

			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void getListOfRooms(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			Authenticate auth = request.getAuth();
			if (connectedClients.containsKey(auth.getUsername())) {
				List<Room> listRooms = databaseController.getListOfRooms();
				if(listRooms == null){
					System.out.println("Smack! You got served");
				}
				Request response = new Request(null, listRooms);
				response.setMethod(Method.GET_LIST_OF_ROOMS_RESPONSE);
				System.out.println("Boom shakalakka");
				send(data.x, response);
			} else {
				Request response = new Request(null, null);
				response.setMethod(Method.LOGIN_FAILED);
				send(data.x, response);
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void cancelView(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			String username = request.getAuth().getUsername();
			String cancelViewOfUser = (String) request.getObject();
			if (connectedClients.containsKey(username)) {
				views.remove(cancelViewOfUser);
				databaseController.unsubscribeToCalendar(username,
						cancelViewOfUser);
				Request response = new Request(null, null);
				response.setMethod(Method.CANCEL_VIEW_SUCCEDED);
				send(data.x, response);

			} else {
				Request response = new Request(null, null);
				response.setMethod(Method.LOGIN_FAILED);
				send(data.x, response);

			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void deleteMeeting(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			String username = request.getAuth().getUsername();
			if (connectedClients.containsKey(username)) {
				Integer id = (Integer) request.getObject();
				databaseController.deleteMeeting(id);
				// send response ?
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void deleteAppointment(Tuple<Socket, Object> data) {
		try {
			Request request = (Request) data.y;
			String username = request.getAuth().getUsername();
			if (connectedClients.containsKey(username)) {
				Integer id = (Integer) request.getObject();
				databaseController.deleteAppointment(id);
			} else {
				// Hmm ?
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void inspectRequest(Tuple<Socket, Object> data) {
		Class<? extends Object> clazz = data.y.getClass();
		String objectName = clazz.getSimpleName();
		System.out.println("ObjectName: " + objectName);
		// This should be a request
		Request request = (Request) data.y;
		Request.Method requestType = request.getMethod();

		if (requestType == Request.Method.AUTHENTICATE) {
			System.out.println("Ready for magic");
			authenticate(data);
		} else if (requestType == Request.Method.GET_USERS) {
			System.out.println("Ready for magic getUsers");
			getUsers(data);
		} else if (requestType == Request.Method.GET_FULL_USER) {
			System.out.println("readu for magic getFullUser");
			getFullUser(data);
		} else if (requestType == Request.Method.SAVE_MEETING) {
			System.out.println("Ready for magic saveMeeting");
			saveMeeting(data);
		} else if (requestType == Request.Method.SAVE_APPOINTMENT) {
			System.out.println("Ready for magic; save appointment");
			saveAppointment(data);
		} else if (requestType == Method.DISPATCH_MEETING_REPLY) {
			System.out.println("Ready for magic; DispatchMeetingReply");
			dispatchMeetingReply(data);
		}else if(requestType == Method.GET_LIST_OF_ROOMS){
			System.out.println("Ready for magic; getListOfRooms");
			getListOfRooms(data);
		}else if(requestType == Method.CANCEL_VIEW){
			System.out.println("Ready for magic; cancelView");
			System.out.println("Not tested yet");
			cancelView(data);
		}

	}

}
