package no.ntnu.fp.net.network;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.storage.db.Database;

/**
 *Handle request from the clients 
 *
 * @author bj0rn
 *
 */


public class Worker extends Database implements Runnable {
	//Fields
	private BlockingQueue<String> inQueue;
	private HashMap<Integer, Socket> test;
	
	
	//Constructor
	public Worker(BlockingQueue<String> inQueue, HashMap<Integer, Socket> test){
		//Setup connection to db
		super();
		System.out.println("The connection is made...");
		this.inQueue = inQueue;
		this.test = test;
	}
	
	//Methods
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Created worker thread");
		while(true){
			try {
				//Sleep
				Thread.currentThread().sleep(5000);
				System.out.println("Print hei");
				if(!inQueue.isEmpty()){
					String data = inQueue.poll();
					System.out.println("Data: "+data);
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	}
	
	public void handleRequest(String data){
		//Determine type of operation
		//Who requested the operation ? 
		//Should this thread answer as well ?
		
		//I should map all the clients!!!
		
		
	}
	
	private synchronized Socket get(int key){
		return test.get(key);
	}
	
}
