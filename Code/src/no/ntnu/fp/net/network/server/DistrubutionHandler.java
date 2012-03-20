package no.ntnu.fp.net.network.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import no.ntnu.fp.model.XmlHandler;



public class DistrubutionHandler {
	//Fields
	private XmlHandler xmlhandler;
	private ArrayList <String> participants;
	private HashMap<String, Socket> connectedClients;
	// K = clientID, V = socket
	private HashMap<String, String> connectedUsers;
	// K = userID, V = clientID
	private DataOutputStream os;
	// connectedClients is a mapping between connectionID (the hash of the socket) and the socket
	// connectedUsers is a mapping between a user's ID and their last used active socket
	public DistrubutionHandler(HashMap<String, Socket> connectedClients){
		this.connectedClients = connectedClients;
	}
	
	/**
	 * updates the connectedUsers table by removing the entry on the key if any
	 * SHOULD BE MOVED TO SERVERCONTROLLER
	 * @param clientID
	 * @param userID	
	 */
	public void updateUserConnection(String clientID, String userID) {
		connectedUsers.remove(userID);
		if (userID != null) {
			connectedUsers.put(userID, clientID);
		}
	}
	
	public void removeUserConnection(String clientID) {
		
		
	}
	/**
	 * Attempts to distribute the given xml to the given user.
	 * Does nothing if the user is not currently logged in on an active connection
	 * @param userID
	 * @param xml
	 */
	public void attemptDistributeToUser(String userID, String xml) {
		String s = connectedUsers.get(userID);
		if (s != null)
			distribute(xml, s);
		return;
	}
/**
 * should send the xml-data to the appropriate client through the appropriate socket
 *  
 * @param xml
 * 	the xml-data to be sent to the client
 * @param clientID
 * 	the receiving client's ID
 */
	public void distribute(String xml, String clientID) {
		Socket socket = connectedClients.get(clientID);
		try {
			os = new DataOutputStream(socket.getOutputStream());
			os.writeUTF(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void distrubute(String xml) {
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
