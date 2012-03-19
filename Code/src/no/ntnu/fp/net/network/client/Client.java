package no.ntnu.fp.net.network.client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.net.network.TestCommunicationClient;
import no.ntnu.fp.net.network.server.ReceiveWorker;


/**/
public class Client {
	//fields 
	private Socket mySocket;
	private int port;
	private BlockingQueue<String > inQueue;
	
	//Constructor 
	public Client(){
		//Do nothing
	}
	
	public void connect(String host, int port){
		
		//TODO: Check if this actually work
		try {
			mySocket = new Socket(InetAddress.getByName(host), port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//spawn new worker 
		System.out.println("Spawn new worker");
		(new Thread(new ClientWorker(mySocket, inQueue))).start();
		//Spawn new receiver 
		System.out.println("Spawn new receiver");
		(new Thread(new InternalReceiver(mySocket, inQueue))).start();
		//(new Thread(new ReceiveWorker(mySocket))).start();
		System.out.println("Some test shit");
		(new Thread(new TestCommunicationClient(mySocket))).start();
		
	}
	
	

}
