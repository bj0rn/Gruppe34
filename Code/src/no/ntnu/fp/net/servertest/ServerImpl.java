package no.ntnu.fp.net.servertest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.Connection;

public class ServerImpl implements Connection  {
	//Fields
	private ServerSocket Serversocket;
	private Socket clientSocket;
	private DataOutputStream os;
	private DataInputStream in;
	private int port;
	
	
	public ServerImpl(int  port){
		this.port = port;
		//socket = new ServerSocket(port);
		
	}


	@Override
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		// TODO Auto-generated method stub
		
	}


	/** Create a new socket and wait for incoming connections. Spawn a new thread
	 * whenever a connection occur. 
	 * 
	 * */
	public void accept() throws IOException, SocketTimeoutException {
		
		
		
		// TODO Auto-generated method stub
		
	}


	@Override
	public void send(String msg) throws ConnectException, IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String receive() throws ConnectException, IOException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
