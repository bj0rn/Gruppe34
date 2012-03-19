package no.ntnu.fp.net.network;

import no.ntnu.fp.net.network.client.Client;

public class TestClient {
	
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 1337;
		
		Client c = new Client();
		c.connect(host, port);
		System.out.println("Connected!!");
		
		
		
	}
}
