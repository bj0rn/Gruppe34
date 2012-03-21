package no.ntnu.fp.net.network;

import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.net.network.client.Client;
import no.ntnu.fp.net.network.client.ClientWorker;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.net.network.client.InternalReceiver;

public class TestClient {
	
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 1337;
		LinkedBlockingDeque<Object> testQueue = new LinkedBlockingDeque<Object>();
		//CommunicationController communicationController = new CommunicationController(mySocket, testQueue)
		Client c = new Client(host, port, testQueue);
		CommunicationController communicationController = c.getCommunicationController();
		//start client thread
		new Thread(c).start();
		
		System.out.println("Connected!!");
		
		
		//Spawn threads
		System.out.println("Send data");
		communicationController.authenticate("bjorn", "lol");
		System.out.println("Data sent");
		
	}
}
