package no.ntnu.fp.net.network.server;

import java.util.ArrayList;

public class XmlToSqlHandler {
	//fields
	private boolean distrubute = true;
	private ArrayList<String> participants;
	
	
	//Constructor
	public XmlToSqlHandler(){
		
	}
	
	
	
	public void dummy(){
		System.out.println("Creating sql");
		System.out.println("Carry out operation on database");
		System.out.println("The operation will always be distribuated whenever data" +
				"is sent back");
		if(distrubute){
			System.out.println("Performing distrubution");
			
		}
		else {
			System.out.println("Simple operation");
		}
		
	}
	
	public void insert(String xml){
		//TODO: ADD from to participants
		System.out.println("insert");
	}
	
	public void delete(String xml){
		System.out.println("Delete");
	}
	
	public void update(String xml){
		System.out.println("Update");
	}
	
	public void get(String xml){
		System.out.println("Get");
	}
	
	public void distribute(String xml){
		System.out.println("Distribute");
		DistrubutionHandler distrubutionHandler = new DistrubutionHandler(null);
		distrubutionHandler.distrubute(xml);
	}
}
