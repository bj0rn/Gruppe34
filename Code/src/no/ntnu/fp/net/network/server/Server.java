package no.ntnu.fp.net.network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import no.ntnu.fp.net.co.Connection;

import no.ntnu.fp.net.network.TestCommunicationServer;
import no.ntnu.fp.net.network.Tuple;
import nu.xom.Attribute;


//Okey.....this is actually a controller, but for now, keep on going
public class Server implements Runnable{
	//Fields
	private static int listenPort = 1337;
	private String addressServer = "localhost";
	private InetAddress localAddress;
	private ArrayList<Socket> connectedHosts;
	private BlockingQueue <Tuple <Socket, Object>> inQueue;
	private Socket newSockfd;
	private ServerSocket sockfd;
	private ArrayList<Socket>  connectedClients;
	private Map<Object, Socket> mapClient;
	HashMap<String, Socket> clients;
	
	private boolean run = true;
	//Constructor
	public Server(){
		connectedClients = new ArrayList<Socket>();
		inQueue = new LinkedBlockingQueue<Tuple<Socket, Object>>();
		clients  = new HashMap<String, Socket>();
	}
	
	public void startServer(){
		//Set up the address
		try {
			localAddress = InetAddress.getByName("localhost");
			sockfd = new ServerSocket(listenPort);
			//Start the server thread
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		startServer();
		System.out.println("Start the worker thread..");
		(new Thread(new Worker(inQueue, clients))).start();
		System.out.println("Test communication thread started");
		(new Thread(new TestCommunicationServer(clients, connectedClients))).start();
		while(true){
			try {
				System.out.println("Waiting for connections");
				newSockfd = sockfd.accept();
				System.out.println("Got connection");
				connected();
				System.out.println("Spawn a new client handler");
				//Spawn new clientHandler
				(new Thread(new ClientHandler(newSockfd, connectedClients.size(), inQueue,clients ))).start();
				//put(connectedClients.size(), newSockfd);
				System.out.println("test");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void connected(){
		connectedClients.add(newSockfd);
		//System.out.println("There are "+ connectedClients.size() + " So far");
		
	}
	
	
	
}
