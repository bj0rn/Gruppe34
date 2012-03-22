package no.ntnu.fp.model;

import java.io.Serializable;

public abstract class Location implements Serializable {
	 
	private static final long serialVersionUID = -993121530050779684L;
	
	private int id;
	protected String description;
	
	public Location(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
