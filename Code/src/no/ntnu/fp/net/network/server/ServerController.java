package no.ntnu.fp.net.network.server;

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
	private DataOutputStream os;
	private ObjectOutputStream oos;
	
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
			os = new DataOutputStream(data.x.getOutputStream());
			oos = new ObjectOutputStream(os);
//			System.out.println("Heisann");
//			//TODO: The database is removed for testing purposes
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
			os = new DataOutputStream(data.x.getOutputStream());
			oos = new ObjectOutputStream(os);
			String xml = (String) data.y;
			String userData[] = XmlHandler.loginFromXml(xml);
			if(connectedClients.containsKey(userData[0])){
				//send successfull login
				//List <User> users = databaseController.getListOfUsers();
				//send the data to the client
				//oos.writeObject(users);
				
			}else {
				oos.writeObject(new String(XmlHandler.loginUnsucessful()));
				//TODO: Create the error message
				//Send error message to client
			}
			//Close the streams
			os.close();
			oos.close();
			
			//The request is XML ? The Object should in some cases be a tuple :) 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getFullUser(Tuple <Socket, Object> data){
		try{
			os = new DataOutputStream(data.x.getOutputStream());
			oos = new ObjectOutputStream(os);
			String xml = (String)data.y;
			String userData[] = XmlHandler.loginFromXml(xml);
			if(connectedClients.containsKey(userData[0])){
				//auth okey...send data
				User user = databaseController.getFullUser(userData[0]);
				oos.writeObject(user);
			}else {
				//Auth not okey...send error message
				oos.writeObject(new String("Error"));
			}
			os.close();
			oos.close();
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void inspectRequest(Tuple <Socket, Object> data){
		System.out.println("Test database");
		
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
//		//Standard xml
//		else if(data.y instanceof String){
//			String method = XmlHandler.inspectMethod((String)data.y);
//			if(method == GET_USERS){
//				getUsers(data);
//			}
//			else if(method == GET_CALENDAR){
//				//Call get calendar
//			}
//		}
//		//TODO: CalendarEntry
//		
//		//TODO: 
		
		
	}
	
	
}
