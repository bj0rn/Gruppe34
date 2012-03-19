package no.ntnu.fp.net.network.client;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import com.sun.corba.se.pept.transport.Connection;


public class ClientWorker implements Runnable {
	//fields
	//private HashMap<String, Socket> clients;
	private BlockingQueue<String> inQueue;
	private Socket mySocket;
	private Communication communication;
	
	
	//Constructor
	public ClientWorker(Socket mySocket, BlockingQueue<String> inQueue){
		this.mySocket = mySocket;
		this.inQueue = inQueue;
		communication = new Communication(mySocket);
	}
	
	
	//methods
	@Override
	public void run() {
		while(true){
			//Sleep
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Pop from queue, if it contain any elements 
			if(!inQueue.isEmpty()){
				String xml = inQueue.poll();
				handle(xml);
			}
		}
		}
	
	
	public void handle(String xml){
		//Inspect header
		if(xml.equals("Update")){
			System.out.println("Update gui");
		}
		else if(xml.equals("Insert")){
			communication.receive(xml);
		}
		//Use Communication to send 
		
		System.out.println("Not implemented!!!");
		//Not implemented 
	}

}
