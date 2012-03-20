package no.ntnu.fp.net.network.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;




public class DistrubutionHandler {
	//Fields
	//private XmlHandler xmlhandler;
	private ArrayList <String> participants;
	private HashMap<String, Socket> connectedClients;
	private DataOutputStream os;
	
	public DistrubutionHandler(HashMap<String, Socket> connectedClients){
		this.connectedClients = connectedClients;
	}
	
	
	public void distrubute(String xml){
		//Run through paticaipants and send xml to clients
		for(String p : participants){
			if(connectedClients.containsKey(p)){
				Socket sockfd = connectedClients.get(p);
				try {
					os = new DataOutputStream(sockfd.getOutputStream());
					os.writeUTF("Notification");				
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
}
