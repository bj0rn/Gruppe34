package no.ntnu.fp.model;

import java.io.Serializable;
import java.util.Date;

public class Room extends Location implements Serializable{
	
	private static final long serialVersionUID = -63886587424231393L;
	
	private SortedDistinctTimeList<Duration> timeTable = new SortedDistinctTimeList<Duration>();
	
	private String name; //id/pk
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
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String toString() {
		return "Rom: " + name;
	}
	
	public String getTimeTable() {
		
		StringBuilder builder = new StringBuilder();
		for (Duration d : timeTable) {
			builder.append(d + "\n");
		}
		return builder.toString();
	}
	
	public void addReservedTime(Date from, Date to) {
		if (from != null && to != null)
			timeTable.add(new Duration(from, to));
		else
			return;
	}
	
	/**
	 * Assuming timeTa
	 * @param from
	 * 		start date
	 * @param to
	 * 		end date
	 * @return
	 * 		availability
	 */
	public boolean isAvailable(Date from, Date to) {
		if (!(from == null) && !(to == null)) {
			Duration duration = new Duration(from, to);
			return !timeTable.overlaps(duration);
		} else {
			return true;
		}
	}

}
