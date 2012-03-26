package no.ntnu.fp.net.network.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.lang.reflect.ParameterizedType;

import org.jdom.adapters.XML4JDOMAdapter;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.XmlHandler;
import no.ntnu.fp.net.network.Request;
import no.ntnu.fp.net.network.Request.Method;




//Static ? 

/**
 * @author bj0rn
 * This class provides the interface for the communication.
 * The methods can be used by the client to produce requests to the server
 * The methods are made to fake synchronization between the client and the server
 * 
 * **/
//TODO: Find a way to notify the client model about notifications





public class CommunicationController {
	
	public final static String HOST = "127.0.0.1";
	public final static int PORT = 1337;

	private static CommunicationController instance;
	//fields
	private BlockingQueue<Object> inQueue;
	private Socket mySocket;
	private UpdateHandler updateHandler;
	private LinkedBlockingDeque<Object> testQueue;
	
	private Authenticate auth;

	
	private DataOutputStream os;
	private ObjectOutputStream oos;
	
	final static String USER = "User";
	
	
	
	public static void main(String[] args) {
	
		User u = new User("bjorn", "123");
		
		
		List <User> users = new ArrayList<User>();
		users.add(u);
		
		
		List test = (List) users;
		Object obj = test.get(0);
		System.out.println(obj.getClass().getSimpleName());
		
	
	}
	
	
	
	//constructor 
	private CommunicationController(){
		
		
		LinkedBlockingDeque<Object> testQueue = new LinkedBlockingDeque<Object>();
		//CommunicationController communicationController = new CommunicationController(mySocket, testQueue)
		Client c = new Client(HOST, PORT, testQueue, this);
		
		try {
			this.mySocket = c.getSocket();
			os = new DataOutputStream(mySocket.getOutputStream());
			updateHandler = new UpdateHandler();
			this.testQueue = testQueue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(c).start();
	}
	
	public static CommunicationController getInstance() {
		if (instance == null) {
			instance = new CommunicationController();
		}
		
		return instance;
	}
	
	
	public void send(Socket socket, Object obj){
		DataOutputStream os;
		try {
			os = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void inspect(){
		//When notifications arrive we need to be ready
		//How can we handle them ?
	}
	
	
	
	
	/**
	 * This method will authenticate will login in the user to the server
	 * **/
	public boolean authenticate(Authenticate auth){
		try {
			Request request = new Request(auth, null);
			request.setMethod(Request.Method.AUTHENTICATE);
			send(mySocket, request);
			setAuthunticate(auth);
			//Wait for response
			boolean good = false;
			int i = 0;
			while(!good){
				Request response = (Request)testQueue.takeFirst();
				System.out.println("Number of tries: "+i++);
				if(response.getMethod() == Request.Method.LOGIN_SUCCEDED){
					return true;
				}else if(response.getMethod() == Request.Method.LOGIN_FAILED){
					return false;
				}
				else {
					testQueue.putLast((Object)response);
				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
}

	
	private void setAuthunticate(Authenticate auth) {
		this.auth = auth;		
	}
	
	public Authenticate getAuthenticate() {
		return auth;
	}



	/**
	 * This method will get all the users from the server
	 * **/
	public List <User> getUsers(){
		try {
			Request request = new Request(auth, null);
			request.setMethod(Request.Method.GET_USERS);
			send(mySocket, request);
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				Request response = (Request) testQueue.takeFirst();
				if(response.getMethod() == Request.Method.GET_USERS_RESPONSE){
					return (List<User>)response.getObject();
				}else if (response.getMethod() == Request.Method.LOGIN_FAILED){
					return null;
				}else{
					//Put it back and try again
					testQueue.putLast((Object)response);
				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public User getFullUser(String user){
		
		
		
		try {
			Request request = new Request(auth, user);
			request.setMethod(Request.Method.GET_FULL_USER);
			send(mySocket, request);
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				Request response = (Request)testQueue.takeFirst();
				if(response.getMethod() == Request.Method.GET_FULL_USER_RESPONSE){
					return (User)response.getObject();
				}
				else if(response.getMethod() == Request.Method.LOGIN_FAILED){
					return null;
				}else {
					testQueue.putLast((Object)response);
				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public boolean saveMeeting(Meeting meeting){
		try{
			Request request = new Request(auth, meeting);
			request.setMethod(Request.Method.SAVE_MEETING);
			send(mySocket, request);
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				//This should be a response containing the key
				Request response = (Request)testQueue.takeFirst();
				if(response.getMethod() == Request.Method.SAVE_MEETING_RESPONSE){
					Integer key = (Integer)response.getObject();
					System.out.println("Got key "+key);
					meeting.setID(key);
					return true;
				}else if(response.getMethod() == Request.Method.LOGIN_FAILED){
					return false;
				}
				else {
					testQueue.putLast((Object)response);
				}
			}		
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public boolean saveAppointment(Appointment appointment){
		try{
			Request request = new Request(auth, appointment);
			request.setMethod(Request.Method.SAVE_APPOINTMENT);
			//Does saveAppointment return a key
			send(mySocket, request);
			int i = 0;
			while(true){
				System.out.println("Number of tries ");
				//This response should contain a key
				Request response = (Request)testQueue.takeFirst();
				if(response.getMethod() == Request.Method.SAVE_APPOINTMENT_RESPONSE){
					Integer id = (Integer)response.getObject();
					appointment.setID(id);
					return true;
				}else if(response.getMethod() == Request.Method.LOGIN_FAILED){
					return false;
				}
				else {
					testQueue.putLast((Object)response);
				}
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
public boolean dispatchMeetingReply(User user, Meeting meeting, State state) {
	try{
		//Gather information 
		String userInfo[] = {
			user.getUsername(),
			"",
		};
		
		String dataValues[] = {
			user.getId(),
			String.valueOf(meeting.getID()),
			state.toString()
		};
		//Pack and send
		String xml = XmlHandler.dispatchMeetingReplyToXml(userInfo, dataValues, "dispatchMeetingReply");
		Request request = new Request(auth, xml);
		request.setMethod(Request.Method.DISPATCH_MEETING_REPLY);
		send(mySocket, request);
		int i = 0;
		//Wait for response
		while(true){
			Request response = (Request)testQueue.takeFirst();
			if(response.getMethod() == Method.SAVE_APPOINTMENT_RESPONSE){
				return true;
				
			}else if(response.getMethod() == Method.LOGIN_FAILED){
				return false;
			}else {
				testQueue.putLast((Object)response);
			}
		}
	
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
		return false;
}

public boolean deleteMeeting(){
	return true;
}
	
public boolean deleteAppointment(){
	return true;
}


public boolean deleteUser(){
	return true;
}



public ArrayList<Location> getListOfRooms() {
	// TODO Auto-generated method stub
	return null;
}
	
	
}
