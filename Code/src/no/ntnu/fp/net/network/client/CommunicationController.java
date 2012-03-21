package no.ntnu.fp.net.network.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.lang.reflect.ParameterizedType;

import no.ntnu.fp.model.Authenticate;
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
	public CommunicationController(Socket mySocket, LinkedBlockingDeque<Object> testQueue){
		
		try {
			this.mySocket = mySocket;
			os = new DataOutputStream(mySocket.getOutputStream());
			updateHandler = new UpdateHandler();
			this.testQueue = testQueue;
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
			oos = new ObjectOutputStream(os);
			oos.writeObject(new String(XmlHandler.generateRequest(username, password, "getUsers")));
			int i = 0;
			while(true){
				System.out.println("Number of tries: "+i++);
				Object obj = testQueue.takeFirst();
				if(obj instanceof List){
					//I  know this a list, but what does it contain ? 
					//Jada jada : P eg vett :P 
					List tmp = (List)obj;
					Object test = tmp.get(0);
					if(test.getClass().getSimpleName() == USER){
						return (List <User>) obj;
					}else {
						//This should not happen
						testQueue.putLast(obj);
					}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
}
