package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Meeting extends CalendarEntry{
	
	public enum State {
		Accepted, Rejected, Pending
	}
	
	private ModelChangeListener modelChangeListener;
	
	private PropertyChangeSupport pcs;
	
	private Map<User, State> participants;
	
	public Meeting(String description){
		super(description);
	}
	
	public Meeting(Date start, Date end, String description) {
		super(description, start, end);
	}
	
	public void addParticipant(User user, State state){
		participants.put(user, state);
	}
	
	public void addParticipants(Map<User, State> participants) {
		
		for(User key : participants.keySet()) {
			State state = participants.get(key);
			addParticipant(key, state);
		}
		
	}

	public boolean removeParticipant(User user){
		if (participants.containsKey(user)) {
			participants.remove(user);
			return true;
		} else {
			return false;
		}
	}

}
