package no.ntnu.fp.net.network;
import java.net.Socket;


/**/
public class Client {
	//fields 
	private Socket mySocket;
	private int port;
	
	//Constructor 
	public Client(){
		
	}
	
	//Methods
	public void run(){
		
	}
	
	public void connect(String host, int port){
		mySocket = new Socket();
	
	}
	
	public void send(String msg){
		
	}
	
	//Spawn new receive thread
	public void recv(){
		
	}
	//Spawn a new requestHandler
	public void handleRequest(){
		
	}
}
