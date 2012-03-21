package no.ntnu.fp.model;

public class Room extends Location{
	private String name; //id/pk
	private String description;
	private int capacity;
	
	public Room(int id, String name, String description, int capacity) {
		super(id, description);
		this.name = name;
		this.capacity = capacity;
	}
	public String getName() {
		return name;
	}
	public int getLocationID() {
		return getID();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	

}
