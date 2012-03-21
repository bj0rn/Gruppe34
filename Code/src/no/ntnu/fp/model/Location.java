package no.ntnu.fp.model;

public abstract class Location {
	private int id;
	private String description;
	
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
