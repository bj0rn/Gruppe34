package no.ntnu.fp.net.network.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

//TODO: Communicate with the db
public class ServerController {
	//fields
	private ArrayList <String> users;
	private ArrayList<String> locations;
	private HashMap<String, Socket> connectedClients;
	private  ArrayList<String> participants;
	
	
	//Test Protocoll
	private static String INSERT = "INSERT";
	private static String DELETE = "DELETE";
	private static String DISTRUBUTE = "DISTRUBUTE";
	private static String GET= "GET";
	private static String UPDATE = "UPDATE";
	
	private DistrubutionHandler distrubuteHandler;
	private XmlToSqlHandler xmltosqlhandler;
	//TODO: Speaks directly with the database
	
	//Constructor
	public ServerController(){
		//Get users
		//Get locations
		xmltosqlhandler = new XmlToSqlHandler();
	}
	
	//Methods
	//Methods
		//TODO: Model with parameters
	public void addUser(String user){
			
	}
		
	public void addLocation(String location){
			
	}
		
	public void unpack(){
			
	}
	
	public void inspectRequest(String xml){
		System.out.println("Inspect header");
		System.out.println("Dispatch");
		System.out.println("XML: "+xml);
		if(xml.equals(INSERT)){
			xmltosqlhandler.insert(xml);
		}
		else if(xml.equals(DELETE)){
			xmltosqlhandler.delete(xml);
		}
		else if(xml.equals(DISTRUBUTE)){
			xmltosqlhandler.distribute(xml);
		}
		else if(xml.equals(GET)){
			xmltosqlhandler.get(xml);
		}
		else if(xml.equals(UPDATE)){
			xmltosqlhandler.update(xml);
		}
		
	}
	
	
}
