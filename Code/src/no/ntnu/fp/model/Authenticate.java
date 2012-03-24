package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Authenticate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PropertyChangeSupport pcs;
	
	public static final String USER_PROPERTY = "Username";
	public static final String PASS_PROPERTY = "Password";
	
	//fields
	private String username;
	private String password;
	
	public Authenticate() {
		pcs = new PropertyChangeSupport(this);
	}
	
	//Constructor
	public Authenticate(String username, String password){
		this.username = username;
		this.password = password;
		pcs = new PropertyChangeSupport(this);
	}
	
	public void setPassword(String password){
		String oldValue = this.password;
		this.password = password;
		pcs.firePropertyChange(PASS_PROPERTY, oldValue, this.password);
	}
	
	public void setUsername(String username){
		String oldValue = this.username;
		this.username = username;
		pcs.firePropertyChange(USER_PROPERTY, oldValue, this.username);
		
	}
	
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
}


