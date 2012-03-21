package no.ntnu.fp.net.network.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class InternalReceiver implements Runnable {
	//Fields 
	private DataInputStream is;
	private ObjectInputStream ios;
	private Socket mySocket;
	private LinkedBlockingDeque<Object> testQueue;
	
	//Constructor
	public InternalReceiver(Socket mySocket, LinkedBlockingDeque<Object> testQueue){
		try {
			is = new DataInputStream(mySocket.getInputStream());
			ios = new ObjectInputStream(is);
			this.mySocket = mySocket;
			this.testQueue = testQueue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//Methods
	@Override
	public void run() {
		while(true){
			try {
				
				Object obj = ios.readObject();
				System.out.println("Got data");
				testQueue.putFirst(obj);
				//testQueue.putFirst(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
	}
}
