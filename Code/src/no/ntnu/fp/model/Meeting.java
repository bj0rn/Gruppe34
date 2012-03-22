package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.soap.SOAPBinding.ParameterStyle;


public class Meeting extends CalendarEntry implements Serializable{
	
	private static final long serialVersionUID = 3423853302160071085L;

	public enum State {
		
		Accepted, Rejected, Pending;
		
		public static State getState(String state) {
			
			if (state.equals("Accepted")) 		return Accepted;
			else if (state.equals("Rejected")) 	return Rejected;
			else if (state.equals("Pending")) 	return Pending;
			else {
				assert(false); // only valid input should be provided
				return null;
			}	
		}
	}
	
	private ModelChangeListener modelChangeListener;
	
	private Map<User, State> participants;
	
	public Meeting(int id) {
		super(id);
	}
	
	public Meeting(String description){
		super(description);
		participants = new HashMap<User, State>();
	}
	
	public Meeting(Date start, Date end, String description, int id) {
		super(description, start, end, id);
		participants = new HashMap<User, State>();
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
	
	public int getNumParticipants() {
		return participants.size();
	}
	
	public Set<User> getParticipants() {
		return participants.keySet();
	}
	
	public State getState(User user) {
		return participants.get(user);
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