package no.ntnu.fp.net.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
	//Fields
	private int id;
	private Socket mySocket;
	private DataInputStream is;
	private BlockingQueue<String> inQueue;
	//private DataOutputStream os;
	//Receive messages from the clients
	//Need some request queue, blocked queue
	public ClientHandler(Socket socket, int id, BlockingQueue<String> inQueue){
		this.mySocket = socket;
		this.id = id;
		this.inQueue = inQueue;
		//Create new streams
		try {
			is = new DataInputStream(mySocket.getInputStream());
			//os = new DataOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
	@Override
	public void run() {
		while(true){
			try {
				String data = is.readLine();
				//System.out.println("Got data from client: " + data);
				System.out.println("Put in requestQueue");
				inQueue.put(FormatRequest(data));
				
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


