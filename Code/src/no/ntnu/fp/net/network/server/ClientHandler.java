package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
	//Fields
	private int id;
	private Socket mySocket;
	private DataInputStream is;
	private BlockingQueue<String> inQueue;
	private DataOutputStream os;
	private HashMap<String, Socket> clients;
	String username;
	String password = "123";
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
		//The easyest place to authenticate is in the client handler
		
		try {
			os.writeUTF("Enter user name: ");
			String username = is.readLine();
			os.writeUTF("Enter password");
			String tmpPass = is.readLine();
			if(password.equals(tmpPass)){
				os.writeUTF("Access granted");
			}
			System.out.println("User added");
			clients.put(username, mySocket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true){
			try {
				String data = is.readLine();
				//System.out.println("Got data from client: " + data);
				System.out.println("Put in requestQueue");
				inQueue.put(data);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	
	public String FormatRequest(String data){
		String modified = "";
		modified += Integer.toString(id);
		modified += ": ";
		modified += data;
		return modified;
		
	}
	
}


