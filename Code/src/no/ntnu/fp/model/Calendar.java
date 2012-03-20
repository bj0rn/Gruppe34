package no.ntnu.fp.model;

import java.util.List;


public class Calendar {
	private ModelChangeListener modelChangeListener;
	private List<CalendarEntry> entries;
	
	public void addMeeting(Meeting meeting){
		entries.add(meeting);
	}
	
	public boolean removeMeeting(Meeting meeting){
		return entries.remove(meeting);
		
	}
	
	public Meeting modifyMeeting(Meeting meeting){
		return null;
	}
	
	public void addAppointment(Appointment appointment){
		entries.add(appointment);
	}
	
	public boolean removeAppointment(Appointment appointment){
		return entries.remove(appointment);
	}
	
	public Appointment modifyAppointment(Appointment appointment){
		return null;
	}

}
