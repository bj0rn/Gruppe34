package no.ntnu.fp.net.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class TestCommunicationServer implements Runnable {
	//fields
	private HashMap<String, Socket> clients;
	private ArrayList<DataOutputStream> osList;
	private Scanner input;
	private ArrayList<Socket> clientList;
	private DataOutputStream os;
	
	
	//constructor
	public TestCommunicationServer(HashMap<String, Socket> clients, ArrayList<Socket> connectedClients){
		this.clients = clients;
		input = new Scanner(System.in);
		this.clientList = connectedClients;
	}
	
	private void handleStreams(){
		Iterator<Entry<String, Socket>> it = clients.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Socket> pair = (Map.Entry<String, Socket>)it.next();
			try {
				osList.add(new DataOutputStream(pair.getValue().getOutputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			it.remove();
		}
		
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//handleStreams();
			System.out.println("Message to send to clients: ");
			String msg = input.nextLine();
			for(Socket s: clientList){
				try {
					os = new DataOutputStream(s.getOutputStream());
					os.writeChars(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
		}
	}
	
	//methods
}
