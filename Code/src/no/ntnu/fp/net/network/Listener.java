package no.ntnu.fp.net.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.co.Connection;

/**
 * This class only listens for new connections
 * **/

 public abstract class Listener extends Thread {
	//Fields
	private Socket newConnection;
	private Connection server;
	private ServerSocket socket;
	//Constructor
	public Listener(int port){
		try {
			socket = new ServerSocket(port);
			System.out.println("Server started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Do nothing
	}
	
	//Run the listener
	public void run(){
		while(true){
			//This call will block
			try {
				System.out.println("Waaiting for connection");
				newConnection = socket.accept();
				System.out.println("Got connection");
				new ReceiveWorker(newConnection).start();
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//This will handle the connection
			//connected(newConnection);
		}
	}
	
	public void setServer(Connection server){
		this.server = server;
	}
	
	
	abstract void connected(Socket newConnection);
	//Methods 
	
	
}
