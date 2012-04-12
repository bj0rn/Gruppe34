/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.awt.image.CropImageFilter;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.OAEPParameterSpec;
import javax.sound.sampled.ReverbType;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {
	
    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
        //Call the abstractionConnection 
    	/*
    	 * InternalQueue
    	 * externalQueue
    	 * nextSequence number 
    	 * disconnectRequest = null
    	 * lastDataPacketSent = null
    	 * lastValidPacketReceived = null
    	 * state = State.Closed
    	 * */
    	super();
        this.myPort = myPort;
        this.myAddress = getIPv4Address();
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
    SocketTimeoutException {
    	
    	this.remoteAddress = remoteAddress.getHostAddress();
    	this.remotePort = remotePort;
    	
    	KtnDatagram packetSent = constructInternalPacket(Flag.SYN);
    	KtnDatagram packetRecv = null;
    	
    	packetSent.setDest_port(this.remotePort);
    	packetSent.setDest_addr(this.remoteAddress);
    		
    	while(packetRecv == null) {
			try {
				simplySendPacket(packetSent);
				this.state = State.SYN_SENT;
			} catch (ClException e) {
				e.printStackTrace();
			}
			
			packetRecv = receiveAck();
			
			if (packetRecv != null && !isValid(packetRecv)) {
				packetRecv = null;
			}
    	}
    	
		if(packetRecv.getFlag() == Flag.SYN_ACK){
			sendAck(packetRecv, true);
			this.state = State.ESTABLISHED;
		}
    	
    }

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
        KtnDatagram packetRecv = null;
        //Need to bind port, but for now; use static
        while((packetRecv = receivePacket(true)) != null) {
        	
        	if (packetRecv != null && isValid(packetRecv)) {
        	
	        	remoteAddress = packetRecv.getSrc_addr();
	        	remotePort = packetRecv.getSrc_port();
	        	
	    		if(packetRecv.getFlag() == Flag.SYN){
	    			
	        		state = State.SYN_RCVD;
	        		sendAck(packetRecv, true);
	        		
	        		while(packetRecv == null) {
	        			packetRecv = receiveAck();
	        			
	        			if (packetRecv != null && !isValid(packetRecv)) {
	        				packetRecv = null;
	        			}
	        		}
	
	        		if(packetRecv.getFlag() == Flag.ACK){
	        			this.state = State.ESTABLISHED;
	        			break;
	        		}
	    		}
        	}
    	}
        
        
        
    	return this;
    }

    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
        KtnDatagram packetSend = constructDataPacket(msg);
        KtnDatagram packetRecv;
        //Set some error checking ? 
        
        
        //This is very basic: send one packet and wait for ACK
        //This implementation should be improved
        
        
        //This function will block
        packetRecv = sendDataPacketWithRetransmit(packetSend);
        if(packetRecv.getFlag() == Flag.ACK){
        	System.out.println("Got ACK send");
        	return;
        }
    }

    /**
     * Wait for incoming data.
     * 
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
        
    	KtnDatagram packetRecv = null;
    	
    	while(packetRecv == null) {
    		packetRecv = receivePacket(false);
    	
    		if (packetRecv == null) {
    		
    			packetRecv = receivePacket(true);
    	
    			if (packetRecv != null && packetRecv.getFlag() == Flag.FIN) {
    				
    				state = State.CLOSE_WAIT;
    				
    				sendAck(packetRecv, false);
    				
    				throw new EOFException();
    				
    				//close();
    				
    			}
    			
    		} else {
    			// Motta data.
    		}
    		//Again, this implementation sucks
    		//Remember to handle retransmissions (This is not handled now)
    		//Handle checksum as well
    		/*KtnDatagram packetRecv;
	        KtnDatagram packetSend;
	        packetRecv = receivePacket(false);
	        sendAck(packetRecv, false);
	        return packetRecv.getPayload().toString();*/
    	
    	}
    	return null;
    }

    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
        
    	KtnDatagram packetSend = constructInternalPacket(Flag.FIN);
    	KtnDatagram packetRecv = null;
    	
    	if (state == State.ESTABLISHED) {
    		while (packetRecv == null){  
        		try {
        			simplySendPacket(packetSend);
        			state = State.FIN_WAIT_1;
    			
        		} catch (ClException e) {
        			e.printStackTrace();
        		}
        		
        		packetRecv = receiveAck();
        	}
        	
        	state = State.FIN_WAIT_2;
        	
        	packetRecv = receivePacket(true);
        	sendAck(packetRecv, false);
        	state = State.TIME_WAIT;
        	
        	try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
    	} else if (state == State.CLOSE_WAIT) {
    		
    		while (packetRecv == null){  
        		try {
        			simplySendPacket(packetSend);
        			state = State.LAST_ACK;
    			
        		} catch (ClException e) {
        			e.printStackTrace();
        		}
        		
        		packetRecv = receiveAck();
        	}
    	}   		
    	
    	state = State.CLOSED;
    	
    	System.out.println("Ignore close for now");
    	
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    protected boolean isValid(KtnDatagram packet) {
    	System.out.println("checking packet for validity...");
    	long calc = packet.calculateChecksum();
    	long chk = packet.getChecksum();
    	System.out.println("packet checksum: "+ chk);
    	System.out.println("calculated checksum: " + calc);
    	boolean res = (calc == chk);
    	System.out.println("And their equality is..." + res + "!");
        return res;
    }
}