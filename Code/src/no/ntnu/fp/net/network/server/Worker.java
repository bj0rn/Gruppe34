package no.ntnu.fp.net.network.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.net.network.Tuple;

/**
 *Handle request from the clients 
 *
 * @author bj0rn
 *
 */


public class Worker implements Runnable {
	//Fields
	private BlockingQueue<Tuple <Socket, Object>> inQueue;
	private HashMap<String, Socket> clients;
	private Scanner input;
	private DataOutputStream os;
	
	private ServerController serverController;
	
	
	//Constructor
	public Worker(BlockingQueue<Tuple<Socket, Object>> inQueue, HashMap<String, Socket> clients){
		//Setup connection to db
		//super();
		System.out.println("The connection is made...");
		this.inQueue = inQueue;
		this.clients = clients;
		input = new Scanner(System.in);
		serverController = new ServerController(clients);
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
					Tuple<Socket, Object> data = inQueue.poll();
					System.out.println("Got data from queue");
					System.out.println("Type of data: "+data.y.getClass().getName());
					
					handleRequest(data);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	}
	
	public void handleRequest(Tuple<Socket, Object> data){
		serverController.inspectRequest(data);
		
	
	}
	
	
	
}
