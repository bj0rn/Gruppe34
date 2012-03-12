package no.ntnu.fp.net.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import no.ntnu.fp.net.co.Connection;

public class NetworkImpl implements Connection{
	//Fields
	private ServerSocket serviceSocket = null;
	private Socket clientSocket = null;
	private DataInputStream is;
	private DataOutputStream os;
	private int port;
	boolean recv = true;
	//Constructor
	public NetworkImpl(int port){
		this.port = port;
	}
	
	
	private NetworkImpl(Socket socket, int port) throws IOException{
			this.port = port;
			this.clientSocket = socket;
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			recv = true;
			System.out.println("Connection  established");
	}
	
	//Method
	
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		
		//Some debugging information
		System.out.println("Trying to connect to: "+remoteAddress.getHostAddress());
		System.out.println("Port: "+remotePort);
		
		clientSocket = new Socket(remoteAddress, remotePort);
		os = new DataOutputStream(clientSocket.getOutputStream());
		is = new DataInputStream(clientSocket.getInputStream());
		
	}
	
	//Why use a wrapper here ?
	
	//Spawn a new thread here 
	public Connection accept() throws IOException, SocketTimeoutException {
		// TODO Auto-generated method stub
		serviceSocket = new ServerSocket(port);
		//serviceSocket.bind(new InetSocketAddress("localhost", port));
		System.out.println("Created new server socket");
		//This call will block
		clientSocket = serviceSocket.accept();
		serviceSocket.close();
		return new NetworkImpl(clientSocket, port);
	}

	//send will just send a message on the specific socket
	public void send(String msg) throws ConnectException, IOException {
		// TODO Auto-generated method stub
		os.writeUTF(msg);
	}

	public String receive() throws ConnectException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("Start receiving");
		
		//String s = is.readUTF();
		//return s;
		send("Hello world");
		while(recv){
			System.out.println("Ready to recv");
			//String s = is.readUTF();
			
			String s = is.readLine();
			System.out.println("Received the text: "+s);
			System.out.println("Received some message");
			return s;
		}
		
		throw new IOException("Can�t receive. The connection is not received");
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		recv = false;
		try {
			os.close();
			is.close();
			clientSocket.close();
		}catch(UnknownHostException e) {
			System.err.println("Trying to connect to unknown host");
		}
	}
	
}
