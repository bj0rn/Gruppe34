package no.ntnu.fp.net.network;

import no.ntnu.fp.net.network.server.Server;

public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Thread(new Server())).start();
		
	}

}


