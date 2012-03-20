package no.ntnu.fp.net.network.client;



public class ClientUpdateHandler {
	//Fields 
	private Communication communication;
	//private XmlHandler xmlhandler;
	
	
	//Constructor
	public ClientUpdateHandler(Communication communication/*, XmlHandler xmlhandler*/){
		this.communication = communication;
		//this.xmlhandler = xmlhandler;
	}
	
	
	//Need some methods
	
	public boolean authenticate(){
		return false;
	}
	
	public void addMeeting(){
		
	}
	
	public void modifyMeeting(int id){
		
	}
	
	public void removeMeeting(int id){
		
	}
	
	public void addAppointment(){
		
	}
	
	public void removeAppointment(int id){
		
	}
	
	public void modifyAppointment(int id){
		
	}
	
}
