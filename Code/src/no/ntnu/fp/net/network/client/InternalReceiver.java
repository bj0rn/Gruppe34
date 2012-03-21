package no.ntnu.fp.net.network.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class InternalReceiver implements Runnable {
	//Fields 
	//private DataInputStream is;
	//private ObjectInputStream ios;
	private Socket mySocket;
	private LinkedBlockingDeque<Object> testQueue;
	
	//Constructor
	public InternalReceiver(Socket mySocket, LinkedBlockingDeque<Object> testQueue){
		this.mySocket = mySocket;
		this.testQueue = testQueue;
	}
	
	//Methods
	@Override
	public void run() {
		while(true){
			try {
				DataInputStream is = new DataInputStream(mySocket.getInputStream());
				ObjectInputStream ios = new ObjectInputStream(is);
				Object obj = ios.readObject();
				System.out.println("Got data");
				testQueue.putFirst(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
	}
}
