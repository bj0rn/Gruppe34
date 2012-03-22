package no.ntnu.fp.model;

import java.io.Serializable;

public class Authenticate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//fields
	private String username;
	private String password;
	
	//Constructor
	public Authenticate(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
}


