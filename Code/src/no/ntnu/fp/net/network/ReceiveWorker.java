package no.ntnu.fp.net.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import no.ntnu.fp.net.co.Connection;

public class ReceiveWorker extends Thread {
	//Fields
	private Socket c;
	private BlockingQueue q;
	private DataInputStream is;
	private DataOutputStream os;
	//Constructor
	public ReceiveWorker(Socket c){
		this.c = c;
		//this.q = q;
		try {
			os = new DataOutputStream(c.getOutputStream());
			is = new DataInputStream(c.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void run(){
		while(true){
			System.out.println("hei");
			try {
				//Does it block ? YESS!!!!
				String s = is.readLine();
				System.out.println(s);
				//q.add(s);
				System.out.println("Receiving job");
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}
}
