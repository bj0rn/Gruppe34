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
	private Socket mySocket;
	private BlockingQueue<Tuple <Socket, Object>> inQueue;
	private DataOutputStream os;
	private HashMap<String, Socket> clients;
	
	private DataInputStream is;
	private ObjectInputStream ios;
	
	
	
	//Receive messages from the clients
	//Need some request queue, blocked queue
	public ClientHandler(Socket socket, BlockingQueue<Tuple <Socket, Object>> inQueue, HashMap<String, Socket> clients){
		this.mySocket = socket;
		this.inQueue = inQueue;
		this.clients = clients;
		//Create new streams
		try {
			is = new DataInputStream(mySocket.getInputStream());
			ios = new ObjectInputStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Read objects and put them in the queue
	 * **/
	@Override
	public void run() {
		while(true){
			try {
				Object obj = ios.readObject();
				if(obj instanceof String){
					System.out.println("Message: "+(String)obj);
				}
				inQueue.put(new Tuple(mySocket, obj));
				System.out.println("Put data in the queue");
				
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
	
	
	
	
}


