package no.ntnu.fp.model;


public class CalendarEntry {
	private ModelChangeListener modelChangeListener;
	private String description;
	private User owner;
	private Location location;
	
	public CalendarEntry(String description){
		this.description = description;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Location getLocation(){
		return location;
	}

	public enum CalendarEntryType {
		MEETING, APPOINTMENT;
		
		public String toString() {
			switch(this) {
				case MEETING: 		return "Meeting";
				case APPOINTMENT: 	return "Appointment";
				default: return null;
			}
		}
	}
	
	
}
