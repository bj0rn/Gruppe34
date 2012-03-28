package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.net.network.Tuple;

public class ClientHandler implements Runnable {
	//Fields
	private Socket mySocket;
	private Queue<Tuple <Socket, Object>> inQueue;
	//private DataOutputStream os;
	private Map<String, Socket> clients;
	
	//private DataInputStream is;
	//private ObjectInputStream ios;
	
	
	
	//Receive messages from the clients
	//Need some request queue, blocked queue
	public ClientHandler(Socket socket, Queue<Tuple <Socket, Object>> inQueue, Map<String, Socket> clients){
		this.mySocket = socket;
		this.inQueue = inQueue;
		this.clients = clients;
		}
	
	
	
	/**
	 * Read objects and put them in the queue
	 * **/
	@Override
	public void run() {
		boolean running = true;
		while(running){
			try {
				DataInputStream is = new DataInputStream(mySocket.getInputStream());
				ObjectInputStream ios = new ObjectInputStream(is);
				Object obj = ios.readObject();
				
				((LinkedBlockingDeque)inQueue).putFirst(new Tuple(mySocket, obj));
				System.out.println("Got data");
				
			} catch (IOException e) {
				System.out.println("A client closed the connection");
				//TODO: remove user from auth cache
				for (Map.Entry<String, Socket> entry : clients.entrySet()) {
					
					if (entry.getValue() == mySocket) {
						clients.remove(entry.getKey());
						continue;
					}
					
				}
				
				running = false;
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


