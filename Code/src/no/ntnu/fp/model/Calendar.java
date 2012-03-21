package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Calendar implements Iterable<CalendarEntry> {
	private ModelChangeListener modelChangeListener;
	private List<CalendarEntry> entries;
	
	public Calendar() {
		entries = new ArrayList<CalendarEntry>();
	}
	
	public void addMeeting(Meeting meeting){
		entries.add(meeting);
	}
	
	public boolean removeMeeting(Meeting meeting){
		return entries.remove(meeting);
		
	}
	
	public void addAppointment(Appointment appointment){
		entries.add(appointment);
	}
	
	public boolean removeAppointment(Appointment appointment){
		return entries.remove(appointment);
	}
	
	public void addCalendarEntry(CalendarEntry entry) {
		entries.add(entry);
	}

	public int getNumEntries() {
		return entries.size();
	}
	
	public CalendarEntry get(int i) {
		return entries.get(i);
	}

	@Override
	public Iterator<CalendarEntry> iterator() {
		return entries.iterator();
	}
	
}
