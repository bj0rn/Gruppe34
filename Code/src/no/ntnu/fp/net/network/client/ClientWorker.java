package no.ntnu.fp.net.network.client;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.model.XmlHandler;




public class ClientWorker implements Runnable {
	//fields
	//private HashMap<String, Socket> clients;
	private BlockingQueue<Object> inQueue;
	private Socket mySocket;
	private CommunicationController communication;
	private LinkedBlockingDeque<Object> testQueue;
	
	//Constructor
	public ClientWorker(Socket mySocket, LinkedBlockingDeque<Object>testQueue, CommunicationController communicationController){
		this.mySocket = mySocket;
		this.inQueue = inQueue;
		this.testQueue = testQueue;
		communication = communicationController;
	}
	
	
	//methods
	@Override
	public void run() {
		while(true){
			//Sleep
			
			try {
				Thread.currentThread().sleep(5000);
				//Object data = testQueue.takeFirst();
				//handle(data);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void handle(Object data){
		
		
	}

}
