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

import no.ntnu.fp.storage.db.DatabaseController;
import no.ntnu.fp.model.*;
import no.ntnu.fp.net.network.Tuple;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
//TODO: Communicate with the db
public class ServerController {
	//fields
	private HashMap<String, Socket> connectedClients;
	private  ArrayList<String> participants;
	private DatabaseController databaseController;
	private XmlHandler xmlHandler;
	//Streams
	//private DataOutputStream os;
	//private ObjectOutputStream oos;
	
	//Test Protocoll
	private final static String AUTHENTICATE = "Authenticate";
	private final static String GET_USERS = "getUsers";
	private final static String GET_CALENDAR = "getCalendar";
	//GenericXmlSerializer genericXml;
	
	
	//Constructor
	public ServerController(HashMap<String, Socket> clients){
		databaseController = new DatabaseController();
		connectedClients = clients;
	}
	
	public void authenticate(Tuple<Socket, Object> data) throws SQLException{
		Authenticate auth = (Authenticate) data.y;
		String username = auth.getUsername();
		String password = auth.getPassword();
		System.out.println("Username: "+ username);
		System.out.println("Password: "+password);
		try {
			DataOutputStream os = new DataOutputStream(data.x.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(os);
			//Check username and password againts the database 
			if(databaseController.authenticate(username, password)){
				connectedClients.put(username, data.x);
				oos.writeObject(new String(XmlHandler.loginSuccessful()));
				System.out.println("Login completed");
			}else {
				oos.writeObject(new String(XmlHandler.loginUnsucessful()));
				System.out.println("Login failed");
			}
			//os.close();
			//oos.close();
		} catch (IOException e) {
			e.printStackTrace();
	}
		
	}
	
	public void getUsers(Tuple <Socket, Object> data){
		try {
			DataOutputStream os = new DataOutputStream(data.x.getOutputStream());
			 ObjectOutputStream oos = new ObjectOutputStream(os);
			String xml = (String) data.y;
			String userData[] = XmlHandler.loginFromXml(xml);
			
			System.out.println("GetUser user: "+userData[0]);
			if(connectedClients.containsKey(userData[0])){
				//send successfull login
				System.out.println("Ka skjer");
				List <User> users = databaseController.getListOfUsers();
				//send the data to the client
				oos.writeObject(users);
				
			}else {
				oos.writeObject(new String(XmlHandler.loginUnsucessful()));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	//TODO: Implements
	public void getFullUser(Tuple <Socket, Object> data){
		//Data is received as xml
//		try {
			String userInfo[] = XmlHandler.loginFromXml((String)data.y);
			String key = XmlHandler.inspectKey((String)data.y);
			System.out.println("key: "+key);
			if(connectedClients.containsKey(userInfo[0])){
				System.out.println("The user is auth");
				//User user = databaseController.getFullUser(key);
				User user = new User("havard", "HŒvard", 20, 12313213, "test@test.com");
				if(user == null){
					System.out.println("NULL");
				}
				System.out.println("User "+user.getName());
				send(data.x, user);
			}else{
				send(data.x, XmlHandler.loginUnsucessful());
			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
	}	
	
	
	
	
	
	public void inspectRequest(Tuple <Socket, Object> data){
		Class <? extends Object> clazz = data.y.getClass();
		String objectName = clazz.getSimpleName();
		System.out.println("ObjectName: "+objectName);
		
		
		if(objectName.equals("Authenticate")){
			try {
				System.out.println("Test");
				authenticate(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Standard xml
		else if(data.y instanceof String){
			//System.out.println("Enter tha shit");
			String method = XmlHandler.inspectMethod((String)data.y);
			System.out.println("METHOD: "+method);
			if(method.equals("getUsers")){
				System.out.println("Ready");
				getUsers(data);
			}
			else if(method == GET_CALENDAR){
				//Call get calendar
			}
			else if(method.equals("getFullUser")){
				
				System.out.println("Ready for some testing");
				getFullUser(data);
			}
		}
		//TODO: CalendarEntry
		
		//TODO: 
		
		
	}
	
	
}
