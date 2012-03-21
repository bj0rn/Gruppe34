package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.net.network.Tuple;

public class ClientHandler implements Runnable {
	//Fields
	private String clientID;
	private int id;
	private Socket mySocket;
	private BlockingQueue<Tuple <Socket, Object>> inQueue;
	private DataOutputStream os;
	private HashMap<String, Socket> clients;
	
	private DataInputStream is;
	private ObjectInputStream ios;
	
	
	
	//Receive messages from the clients
	//Need some request queue, blocked queue
	public ClientHandler(Socket socket, int id, BlockingQueue<Tuple <Socket, Object>> inQueue, HashMap<String, Socket> clients){
		this.mySocket = socket;
		this.id = id;
		this.inQueue = inQueue;
		this.clients = clients;
		//Create new streams
		try {
			is = new DataInputStream(mySocket.getInputStream());
			ios = new ObjectInputStream(is);
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
				Object obj = ios.readObject();
				if(obj instanceof String){
					System.out.println("Message: "+(String)obj);
				}
				inQueue.put(new Tuple(mySocket, obj));
				System.out.println("Got data");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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


