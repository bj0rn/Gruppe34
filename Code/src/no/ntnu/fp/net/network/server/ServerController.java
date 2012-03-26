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
	//fields
	private Map<String, Socket> connectedClients;
	private  ArrayList<String> participants;
	private DatabaseController databaseController;
	private XmlHandler xmlHandler;
	private Queue <Tuple <Socket, Object>> inQueue;
	//Streams
	//private DataOutputStream os;
	//private ObjectOutputStream oos;
	
	//Test Protocoll
	private final static String AUTHENTICATE = "Authenticate";
	private final static String GET_USERS = "getUsers";
	private final static String GET_CALENDAR = "getCalendar";
	//GenericXmlSerializer genericXml;
	
	
	//Constructor
	public ServerController(Map<String, Socket> clients, Queue <Tuple <Socket, Object>> inQueue){
		databaseController = new DatabaseController();
		connectedClients = clients;
		this.inQueue = inQueue;
	}
	
	
	
	private void send(Socket socket, Object data){
		try {
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void authenticate(Tuple<Socket, Object> data){
		try{
			Request request = (Request)data.y;
			Authenticate auth = request.getAuth();
			String username = auth.getUsername();
			String password = auth.getPassword();
			System.out.println("Username: "+ username);
			System.out.println("Password: "+password);
			//Check username and password againts the database 
			if(databaseController.authenticate(username, password)){
				connectedClients.put(username, data.x);
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_SUCCEDED);
				send(data.x, response);
				System.out.println("Login completed");
			}else {
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}
		}catch(SQLException sq){
			sq.printStackTrace();
		}
	
	}
	
	public void getUsers(Tuple <Socket, Object> data){
		try {
			Request request = (Request)data.y;
			Authenticate auth = request.getAuth();
			
			System.out.println("GetUser user: "+ auth.getUsername());
			if(connectedClients.containsKey(auth.getUsername())){
				List <User> users = databaseController.getListOfUsers();
				Request response = new Request(null,(Object)users);
				response.setMethod(Request.Method.GET_USERS_RESPONSE);
				//send the data to the client
				send(data.x, response);
			}else {
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//TODO: Implements
	public void getFullUser(Tuple <Socket, Object> data){
		try {
			Request request = (Request)data.y;
			Authenticate auth = request.getAuth();
			String username = (String)request.getObject();
			if(connectedClients.containsKey(auth.getUsername())){
				User user = databaseController.getFullUser(username);
				Request response = new Request(null, user);
				System.out.println("User "+user.getName());
				response.setMethod(Request.Method.GET_FULL_USER_RESPONSE);
				send(data.x, response);
			}else{
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, XmlHandler.loginUnsucessful());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}	
	
	
	public void saveMeeting(Tuple <Socket, Object> data){
		try{
			
			Request request = (Request)data.y;
			Meeting meeting = (Meeting)request.getObject();
			String username = meeting.getOwner().getUsername();
			System.out.println("Owner: "+username);
			if(connectedClients.containsKey(username)){
				Integer id = databaseController.saveMeeting(meeting);
				meeting.setID(id);
				//send key to owner
				Request response = new Request(null, id);
				response.setMethod(Request.Method.SAVE_MEETING_RESPONSE);
				send(data.x, response);
				System.out.println("Send data to connected clients");
				
				//also send message to available clients
				System.out.println("Send data to connected clients");
				Set <User> participants = meeting.getParticipants();
				for(User u : participants){
					String user = u.getUsername();
					System.out.println("Participant: "+user);
					if(connectedClients.containsKey(user)){
						Socket sockfd = connectedClients.get(username);
						Request r = new Request(null, meeting);
						r.setMethod(Request.Method.MEETING_NOTIFICATION);
						send(sockfd, r);
					}else{
						//do some stuff in the db ?
						System.out.println("Sorry the client is not connected");
					}
				}
				
			}else {
				//Not authenticated
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}
		}catch(SQLException sq){
			sq.printStackTrace();
		}
		
	}
	
	 public void saveAppointment(Tuple<Socket, Object> data){
		//Got an appointment object
		try {
			Request request = (Request)data.y;
			Authenticate auth = request.getAuth();
			Appointment a = (Appointment)request.getObject();
			System.out.println("user: "+auth.getUsername());
			if(connectedClients.containsKey(auth.getUsername())){
				Integer id = databaseController.saveAppointment(a);
				a.setID(id); 
				Request response = new Request(null, id);
				response.setMethod(Request.Method.SAVE_APPOINTMENT_RESPONSE);
				send(data.x, response);
				
			}else {
				//Not authenticated
				Request response = new Request(null, null);
				response.setMethod(Request.Method.LOGIN_FAILED);
				send(data.x, response);
			}
			
			
		}catch(SQLException sq){
			sq.printStackTrace();
		}
	}
	 
	 
	 
//	 public void savePlace(Tuple <Socket, Object> data){
//		 try {
//			 Place place = (Place)data.y;
//			 
//			 
//			 
//		 }catch(SQLException sq){
//			 sq.printStackTrace();
//		 }
//	 }
	
	
	public void dispatchMeetingReply(Tuple <Socket, Object> data){
		try{
			Request request = (Request)data.y;
			Authenticate auth = request.getAuth();
			if(connectedClients.containsKey(auth.getUsername())){
				String xml = (String)request.getObject();
				ArrayList<String> dataValues = (ArrayList<String>) XmlHandler.dispatchMeetingReplyFromXml(xml);
				String username = dataValues.get(0);
				System.out.println("Userid "+username);
				String meetingId = dataValues.get(1);
				System.out.println("MeetingId "+meetingId);
				String state = dataValues.get(2);
				System.out.println("State "+state);
				//Check with db
				if(databaseController.updateMeetingState(username, meetingId, State.getState(state))){
					//send success
					Request response = new Request(null, null);
					response.setMethod(Method.SAVE_APPOINTMENT_RESPONSE);
					send(data.x, response);
				}else {
					Request response = new Request(null, null);
					response.setMethod(Method.LOGIN_FAILED);
					send(data.x, response);
				}
				
			}
		
		}catch(SQLException sq){
			sq.printStackTrace();
		}
	}
	public void inspectRequest(Tuple <Socket, Object> data){
		Class <? extends Object> clazz = data.y.getClass();
		String objectName = clazz.getSimpleName();
		System.out.println("ObjectName: "+objectName);
		//This should be a request
		Request request  = (Request) data.y;
		Request.Method requestType = request.getMethod();
		
		if(requestType == Request.Method.AUTHENTICATE){
			System.out.println("Ready for magic");
			authenticate(data);
		}else if(requestType == Request.Method.GET_USERS){
			System.out.println("Ready for magic getUsers");
			getUsers(data);
		}else if(requestType == Request.Method.GET_FULL_USER){
			System.out.println("readu for magic getFullUser");
			getFullUser(data);
		}else if(requestType == Request.Method.SAVE_MEETING){
			System.out.println("Ready for magic saveMeeting");
			saveMeeting(data);
		}else if(requestType == Request.Method.SAVE_APPOINTMENT){
			System.out.println("Ready for magic");
			saveAppointment(data);
		}else if(requestType == Method.DISPATCH_MEETING_REPLY){
			System.out.println("Ready for magic DispatchMeetingReply");
			dispatchMeetingReply(data);
		}
		
		
	}
	
	
}
