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
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.XmlHandler;




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
	
	public void inspect(){
		//When notifications arrive we need to be ready
		//How can we handle them ?
	}
	
	
	
	
	/**
	 * This method will authenticate will login in the user to the server
	 * **/
	public boolean authenticate(String username, String password){
		try {
			oos = new ObjectOutputStream(os);
			oos.writeObject(new Authenticate(username, password));
			//Wait for response
			boolean good = false;
			int i = 0;
			while(!good){
				Object obj = testQueue.takeFirst();
				System.out.println("Number of tries: "+i++);
				if(obj instanceof String){
					String status = XmlHandler.inspectStatus((String)obj);
					if(status.equals("200")){
						return true;
					}else if(status.equals("401")){
						return false;
					}
				}
				else {
					//Wrong data, put it back
					testQueue.putLast(obj);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
}

	
	/**
	 * This method will get all the users from the server
	 * **/
	public List <User> getUsers(String username, String password){
		try {
			os = new DataOutputStream(mySocket.getOutputStream());
			oos = new ObjectOutputStream(os);
			oos.writeObject(new String(XmlHandler.generateRequest(username, password, "getUsers")));
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				Object obj = testQueue.takeFirst();
				System.out.println("ObjectName: "+obj.getClass().getName());
				if(obj instanceof List){
					//I  know this a list, but what does it contain ? 
					//Jada jada : P eg vett :P 
					List tmp = (List)obj;
					Object test = tmp.get(0);
					System.out.println("Object name: "+test.getClass().getSimpleName());
					if(test.getClass().getSimpleName().equals("User")){
						System.out.println("heisan");
						return (List <User>) obj;
					}
				}else if(obj instanceof String){
					if(XmlHandler.inspectStatus((String)obj).equals("401")){
						return null;
					}
				}else {
					//This should not happen
					System.out.println("W00t");
					testQueue.putLast(obj);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
	
	public User getFullUser(String myUsername, String myPassword, String user){
		try {
			send(mySocket, XmlHandler.getFullUserToXMl(myUsername, myPassword, user, "getFullUser"));
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				Object obj = testQueue.takeFirst();
				if(obj instanceof User) {
					return (User)obj;
				}
				else if(obj instanceof String){
					if(XmlHandler.inspectStatus((String)obj).equals("401")){
						//Not authenticated
						return null;
					}
				}
				//Not the message we were looking for
				testQueue.putLast(obj);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void saveMeeting(Meeting meeting){
		try{
		//CreateEntry returns a key
		//Need a way to set this in the meeting object
		send(mySocket, meeting);
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				//This should be a response containing the key
				Object obj = testQueue.takeFirst();
				if(obj instanceof String){
					//Check if this is the correct one; the method field should be saveUser
					if(XmlHandler.inspectMethod((String)obj).equals("saveMeeting")){
						//Correct  message
						String key = XmlHandler.inspectKey((String)obj);
						//Set key.....hmm... 
						return;
					}
				}else {
					//Wrong put it back
					testQueue.putLast(obj);
				}
				
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void saveAppointment(Appointment appointment){
		try{
		//Does saveAppointment return a key
			send(mySocket, appointment);
			int i = 0;
			while(true){
				System.out.println("Number of tries ");
				//This response should contain a key
				Object obj = testQueue.takeFirst();
				if(obj instanceof String){
					String key = XmlHandler.inspectKey((String)obj);
					if(key != null){
						appointment.setID(Integer.parseInt(key)); 
					}
					else if(XmlHandler.inspectStatus((String)obj).equals("401")){
						System.out.println("Failed to authenticate");
					}else{
						System.out.println("You are really fucked up this time");
					}
				}else {
					//Not the packet I«m waiting for
					testQueue.putLast(obj);
				}
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
public void dispatchMeetingReply(User user, Meeting meeting, State state) {
		
		user.getId();
		meeting.getID();
		state.toString();
		
		
	}
	
	
	
}
