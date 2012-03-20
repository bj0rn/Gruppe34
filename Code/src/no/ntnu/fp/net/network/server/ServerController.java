package no.ntnu.fp.net.network.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.fp.storage.db.DatabaseController;
import no.ntnu.fp.model.*;
//TODO: Communicate with the db
public class ServerController {
	//fields
	private ArrayList <String> users;
	private ArrayList<String> locations;
	private HashMap<String, Socket> connectedClients;
	private  ArrayList<String> participants;
	private DatabaseController databaseController;
	private DataOutputStream os;
	private XmlHandler xmlHandler;
	
	//Test Protocoll
	private static String INSERT = "INSERT";
	private static String DELETE = "DELETE";
	private static String DISTRUBUTE = "DISTRUBUTE";
	private static String GET= "GET";
	private static String UPDATE = "UPDATE";
	private static String AUTH = "AUTH";
	private DistrubutionHandler distrubuteHandler;
	private XmlToSqlHandler xmltosqlhandler;
	GenericXmlSerializer genericXml;
	
	
	//Constructor
	public ServerController(HashMap<String, Socket> clients){
		//Get users
		//Get locations
		xmltosqlhandler = new XmlToSqlHandler();
		databaseController = new DatabaseController();
		connectedClients = clients;
		genericXml = new GenericXmlSerializer();
				
	}
	
	public void authenticate(String clientID, String xml) throws SQLException, IOException{
		String userData[] = XmlHandler.loginFromXml(xml);
		Socket sockfd = connectedClients.get(clientID);
		os = new DataOutputStream(sockfd.getOutputStream());
		if(databaseController.authenticate(userData[0], userData[1])){
			connectedClients.remove(clientID);
			connectedClients.put(userData[0], sockfd);
			os.writeUTF(XmlHandler.loginSuccessful());
		}else {
			os.writeUTF(xmlHandler.loginUnsucessful());
		}
	}
	
	public void getUsers(String clientID, String xml) throws IOException, SQLException{
		List <User> users;
		String userData[] = XmlHandler.loginFromXml(xml);
		DataOutputStream os;
		System.out.println("User data: "+userData[0]);
		if(connectedClients.containsKey(userData[0])){
			Socket sockfd = connectedClients.get(userData[0]);
			os = new DataOutputStream(sockfd.getOutputStream());
			users = databaseController.getListOfUsers();
			System.out.println("Got the users");
			
			
		}else {
			System.out.println("Not authenticated");
		}
		
	}
	
	
	public void saveMeeting(String xml){
		
	}
	
	
	public void update(String xml){
		
	}
	
	
	
	public void inspectRequest(String xml){
		String res[] = xml.split(" ",2);
		System.out.println("Xml: "+res[1]);
		String test = XmlHandler.inspectMethod(res[1]);
		System.out.println("Test: "+test);
		if(test.equals("login")){
			try {
				authenticate(res[0], res[1]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(test.equals("getUsers")){
			try {
				getUsers(res[0], res[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
