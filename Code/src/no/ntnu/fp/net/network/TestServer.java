package no.ntnu.fp.net.network;

public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Thread(new Server())).start();
		
	}

}
