package no.ntnu.fp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Calendar implements Iterable<CalendarEntry>, Serializable {
	
	private static final long serialVersionUID = 3084624718665667718L;
	
	private ModelChangeListener modelChangeListener;
	private List<CalendarEntry> entries = new ArrayList<CalendarEntry>();
	private User user;
	
	public Calendar(User user) {
		this.user = user;
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
	
	public List<Notification> getMeetingNotifications() {
		
		List<Notification> notifications = new ArrayList<Notification>();
		
		for(CalendarEntry entry : entries) {
			
			if (entry instanceof Meeting) {
				Meeting meeting = (Meeting)entry;
				
				if (meeting.getOwner().equals(user)) {
					
					for (User user : meeting.getParticipants()) {
						
						no.ntnu.fp.model.Meeting.State state = meeting.getState(user);
						
						if (state == no.ntnu.fp.model.Meeting.State.Rejected) {
							notifications.add(new MeetingReplyNotification(user, meeting));
						}
						
					}
					
				} else {
					
					no.ntnu.fp.model.Meeting.State state = meeting.getState(user);
					
					if (state == 	no.ntnu.fp.model.Meeting.State.Pending) {
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
	
}
