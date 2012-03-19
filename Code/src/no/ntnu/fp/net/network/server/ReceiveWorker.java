package no.ntnu.fp.net.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.net.co.Connection;

public class ReceiveWorker extends Thread {
	//Fields
	private Socket mySocket;
	private BlockingQueue<String> inQueue;
	private DataInputStream is;
	//private DataOutputStream os;
	//Constructor
	public ReceiveWorker(Socket mySocket){
		this.mySocket = mySocket;
		//this.q = q;
		try {
			//os = new DataOutputStream(c.getOutputStream());
			is = new DataInputStream(mySocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void run(){
		while(true){
			try {
				//This blocks, no need to sleep 
				String xml = is.readLine(); //TODO: Change this to readUTF
				System.out.println("Adding job");
				inQueue.put(xml);
				System.out.println("Receiving job");
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}
}
