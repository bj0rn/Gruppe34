package no.ntnu.fp.net.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestCommunicationClient implements Runnable {
	//fields
	private Socket mySocket;
	private Scanner input;
	private DataOutputStream os;
	//Constructor
	public TestCommunicationClient(Socket socket){
		mySocket = socket;
		input = new Scanner(System.in);
		try {
			os = new DataOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		//methods
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println("Enter message: ");
			String msg = input.nextLine();
			System.out.println("Message entered: "+msg);
			try {
				os.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
