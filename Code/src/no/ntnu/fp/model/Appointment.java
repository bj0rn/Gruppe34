package no.ntnu.fp.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;

public class Appointment extends CalendarEntry implements Serializable{
	
	private static final long serialVersionUID = -1068480439451430890L;
	private PropertyChangeSupport pcs;
	
	public Appointment(String description) {
		super(description);
		// TODO Auto-generated constructor stub
		pcs = new PropertyChangeSupport(this);
	}

	public Appointment(Date start, Date end, String desc, int id) {
		super(desc, start, end, id);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	


}
