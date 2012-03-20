package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;


public class CalendarEntry {
	private ModelChangeListener modelChangeListener;
	private String description;
	private User owner;
	private Location location;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public final static String MODEL_PROPERTY ="Model";
	public final static String DESC_PROPERTY ="Description";
	public final static String OWNER_PROPERTY ="Owner";
	public final static String LOC_PROPERTY ="Location";
	
	public CalendarEntry(String description){
		this.description = description;
		
	}
	
	public void setLocation(Location location){
		Location oldValue = this.location;
		this.location = location;
		pcs.firePropertyChange(LOC_PROPERTY, oldValue, location);
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void setDescription(String description){
		String oldValue = this.description;
		this.description = description;
		pcs.firePropertyChange(DESC_PROPERTY, oldValue, description);
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setOwner(User owner){
		User oldValue = this.owner;
		this.owner = owner;
		pcs.firePropertyChange(OWNER_PROPERTY, oldValue, owner);
	}
	
	public User getOwner(){
		return owner;
	}
	
	public void setModelCL(ModelChangeListener modelChangeListener){
		ModelChangeListener oldValue = this.modelChangeListener;
		this.modelChangeListener= modelChangeListener;
		pcs.firePropertyChange(MODEL_PROPERTY, oldValue, modelChangeListener);
	}
	
	public ModelChangeListener getModelCL(){
		return modelChangeListener; 
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
