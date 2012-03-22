package no.ntnu.fp.net.network;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.model.User;
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
		
		//Spawn threads
		System.out.println("Send data");
		communicationController.authenticate("havard", "test");
		User user = communicationController.getFullUser("havard", "test", "havard");
		System.out.println("Got user: "+user.getName());
//		List <User> users = communicationController.getUsers("havard", "test");
//		for(User u: users){
//			System.out.println(u.getName());
//			System.out.println(u.getEmail());
//		}
		//communicationController.getUsers("bjorn", "lol");
		System.out.println("Data sent");
		
	}
}
