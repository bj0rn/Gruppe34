package no.ntnu.fp.net.network.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.storage.db.Database;

/**
 *Handle request from the clients 
 *
 * @author bj0rn
 *
 */


public class Worker implements Runnable {
	//Fields
	private BlockingQueue<String> inQueue;
	private HashMap<String, Socket> clients;
	private Scanner input;
	private DataOutputStream os;
	
	private ServerController serverController;
	
	
	//Constructor
	public Worker(BlockingQueue<String> inQueue, HashMap<String, Socket> clients){
		//Setup connection to db
		//super();
		System.out.println("The connection is made...");
		this.inQueue = inQueue;
		this.clients = clients;
		input = new Scanner(System.in);
		serverController = new ServerController();
	}
	
	//Methods
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Created worker thread");
		while(true){
			try {
				//Sleep
				Thread.currentThread().sleep(500);
				if(!inQueue.isEmpty()){
					String data = inQueue.poll();
					System.out.println("Data: "+data);
					handleRequest(data);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	}
	
	public void handleRequest(String data){
		serverController.inspectRequest(data);
		
		
	}
	
	
	
}
