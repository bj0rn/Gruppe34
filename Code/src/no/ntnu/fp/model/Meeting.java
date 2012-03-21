package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Meeting extends CalendarEntry{
	
	public enum State {
		
		Accepted, Rejected, Pending;
		
		public static State getState(String state) {
			
			if (state.equals("Accepted")) 		return Accepted;
			else if (state.equals("Rejected")) 	return Rejected;
			else if (state.equals("Pending")) 	return Pending;
			else {
				assert(false);
				return null;
			}
			
			
		}
		
	}
	
	private ModelChangeListener modelChangeListener;
	
	private PropertyChangeSupport pcs;
	
	private Map<User, State> participants;
	
	public Meeting(String description){
		super(description);
		participants = new HashMap<User, State>();
	}
	
	public Meeting(Date start, Date end, String description, int id) {
		super(description, start, end, id);
	}
	
	public void addParticipant(User user, State state){
		participants.put(user, state);
	}
	
	public void addParticipants(Map<User, State> participants) {
		if (participants != null) {
			for(User key : participants.keySet()) {
				State state = participants.get(key);
				addParticipant(key, state);
			}
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
