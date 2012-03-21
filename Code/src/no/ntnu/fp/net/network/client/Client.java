package no.ntnu.fp.net.network.client;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.net.network.TestCommunicationClient;


/**/
public class Client implements Runnable {
	//fields 
	private Socket mySocket;
	private int port;
	private String host;
	private BlockingQueue<Object > inQueue;
	private DataOutputStream os;
	private ObjectOutputStream oos;
	private CommunicationController communicationController;
	private LinkedBlockingDeque<Object> testQueue;
	
	//Constructor 
	public Client(String host, int port, LinkedBlockingDeque<Object> testQueue){
		this.testQueue = testQueue;
		try {
			this.mySocket = new Socket(InetAddress.getByName(host), port);
			this.communicationController = new CommunicationController(mySocket, testQueue);
			this.host = host;
			this.port = port;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public CommunicationController getCommunicationController(){
		return communicationController;
	}
	
	
	@Override
	public void run() {
		(new Thread(new ClientWorker(mySocket, testQueue, communicationController))).start();
		(new Thread(new InternalReceiver(mySocket, testQueue))).start();
		
	}
	

}
