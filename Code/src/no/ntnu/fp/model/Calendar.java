package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.fp.model.Meeting.State;

public class Calendar implements Iterable<CalendarEntry>, Serializable, PropertyChangeListener {
	
	private static final long serialVersionUID = 3084624718665667718L;
	
	public static final String ENTRIES_PROPERTY = "entries";
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private List<CalendarEntry> entries = new ArrayList<CalendarEntry>();
	private User user;
	
	public Calendar(User user) {
		this.user = user;
	}
	
	public void addMeeting(Meeting meeting){
		entries.add(meeting);
		meeting.addPropertyChangeListener(this);
		pcs.fireIndexedPropertyChange(ENTRIES_PROPERTY, entries.size()-1, null, meeting);
	}
	
	public boolean removeMeeting(Meeting meeting){
		int index = entries.indexOf(meeting);
		
		if(entries.remove(meeting)) {
			pcs.fireIndexedPropertyChange(ENTRIES_PROPERTY, index, meeting, null);
			return true;
		} else {
			return false;
		}
	}
	
	public void addAppointment(Appointment appointment){
		entries.add(appointment);
		appointment.addPropertyChangeListener(this);
		pcs.fireIndexedPropertyChange(ENTRIES_PROPERTY, entries.size()-1, null, appointment);
	}
	
	public boolean removeAppointment(Appointment appointment){
		int index = entries.indexOf(appointment);
		
		if (entries.remove(appointment)) {
			pcs.fireIndexedPropertyChange(ENTRIES_PROPERTY, index, appointment, null);
			return true;
		} else {
			return false;
		}
	}
	
	public void addCalendarEntry(CalendarEntry entry) {
		entries.add(entry);
		entry.addPropertyChangeListener(this);
		pcs.fireIndexedPropertyChange(ENTRIES_PROPERTY, entries.size()-1, null, entry);
	}

	public int getNumEntries() {
		return entries.size();
	}
	
	public CalendarEntry get(int i) {
		return entries.get(i);
	}
	
	public List<Notification> getMeetingNotifications() {
		
		List<Notification> notifications = new ArrayList<Notification>();
		
		for(CalendarEntry entry : entries) {
			
			if (entry instanceof Meeting) {
				Meeting meeting = (Meeting)entry;
				
				if (meeting.getOwner().equals(user)) {
					
					for (User user : meeting.getParticipants()) {
						
						State state = meeting.getState(user);
						
						if (state == State.Rejected) {
							notifications.add(new MeetingReplyNotification(user, meeting));
						}	
					}
				} else {
					
					State state = meeting.getState(user);
					
					if (state == State.Pending) {
						notifications.add(new MeetingInviteNotification(user, meeting));
					}
				}
			}
		}
		
		return notifications;
		
	}

	@Override
	public Iterator<CalendarEntry> iterator() {
		return entries.iterator();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(CalendarEntry entry : entries) {
			builder.append(entry.getDescription() + '\n');
		}
		return builder.toString();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
	}
}
