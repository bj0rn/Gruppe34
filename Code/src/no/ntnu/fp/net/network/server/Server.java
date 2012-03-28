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
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
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
	private Queue <Tuple <Socket, Object>> inQueue;
	private Socket newSockfd;
	private ServerSocket sockfd;
	private Map<Object, Socket> mapClient;
	Map<String, Socket> clients;
	
	private boolean run = true;
	//Constructor
	public Server(){
		inQueue = new LinkedBlockingDeque<Tuple<Socket, Object>>();
		clients  = new ConcurrentHashMap<String, Socket>();
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
		//Start worker thread
		(new Thread(new Worker(inQueue, clients))).start();
		while(true){
			try {
				System.out.println("Waiting for connections");
				newSockfd = sockfd.accept();
				System.out.println("Got new connection");
				(new Thread(new ClientHandler(newSockfd, inQueue,clients ))).start();
				//put(connectedClients.size(), newSockfd);
				System.out.println("test");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
}
