package no.ntnu.fp.net.network.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class InternalReceiver implements Runnable {
	//Fields 
	private BlockingQueue<String> inQueue;
	private DataInputStream is;
	private Socket mySocket;
	
	
	//Constructor
	public InternalReceiver(Socket mySocket, BlockingQueue<String> inQueue){
		try {
			is = new DataInputStream(mySocket.getInputStream());
			this.mySocket = mySocket;
			this.inQueue = inQueue;
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
				String xml = is.readUTF();
				inQueue.put(xml);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
	}
}
