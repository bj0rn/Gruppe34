package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
	//Fields
	private String clientID;
	private int id;
	private Socket mySocket;
	private DataInputStream is;
	private BlockingQueue<String> inQueue;
	private DataOutputStream os;
	private HashMap<String, Socket> clients;
	//Receive messages from the clients
	//Need some request queue, blocked queue
	public ClientHandler(Socket socket, int id, BlockingQueue<String> inQueue, HashMap<String, Socket> clients){
		this.mySocket = socket;
		this.id = id;
		this.inQueue = inQueue;
		this.clients = clients;
		//Create new streams
		try {
			is = new DataInputStream(mySocket.getInputStream());
			os = new DataOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
	@Override
	public void run() {
		//The easiest place to authenticate is in the client handler
		System.out.println("Hash" +mySocket.hashCode());
		clientID = Integer.toString(mySocket.hashCode());
		clients.put(clientID, mySocket);
		
		
		while(true){
			try {
				String data = is.readLine();
				//System.out.println("Got data from client: " + data);
				System.out.println("Put in requestQueue");
				inQueue.put(format(data));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	public String format(String data){
		//the function used to generate clientID returns an integer
		//using regex to match ([0-9]* ) we can extract the clientID
		String tmp = clientID + " " + data;
		return tmp;
	}
	
	
}


