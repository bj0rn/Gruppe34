package no.ntnu.fp.net.network.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;



//Static ? 

//Object for now
public class Communication {
	//fields
	private BlockingQueue<String> inQueue;
	private Socket mySocket;
	private DataOutputStream os;
	private UpdateHandler updateHandler;
	
	
	//constructor 
	public Communication(Socket mySocket){
		
		try {
			this.mySocket = mySocket;
			os = new DataOutputStream(mySocket.getOutputStream());
			updateHandler = new UpdateHandler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//methods
	
	/*
	 * 
	 * */
	public void sendNewModelRequest(String xml){
		try {
			os.writeUTF(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendModifyModelRequest(String xml){
		try {
			os.writeUTF(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendDistributeModelRequest(String xml){
		try {
			os.writeUTF(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*For testing only */
	public void simplySend(String message){
		
		try {
			os.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void receive(String xml){
		updateHandler.handle(xml);
		
		//The message is already received SEE Queue 
		//The update handler should handler receive
	}
	
	
	
}
