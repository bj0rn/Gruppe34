package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.soap.SOAPBinding.ParameterStyle;


public class Meeting extends CalendarEntry implements Serializable {
	
	private static final long serialVersionUID = 3423853302160071085L;

	public static final String STATE_PROPERTY = "State";
	public static final String PARTICIPANTS_PROPERTY = "Participants"; 
	
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
	
	
	private Map<User, State> participants = new HashMap<User, State>();
	
	public Meeting() {
		this(-1);
	}
	
	public Meeting(int id) {
		super(id);
	}
	
	public Meeting(String description){
		super(description);
	}
	
	public Meeting(Date start, Date end, String description, int id) {
		super(description, start, end, id);
	}
	
	public Meeting(Date start, Date end, String description, int id, Location location) {
		super(description, start, end, id, location);	
	}
	
	public void addParticipant(User user, State state){
		participants.put(user, state);
		pcs.firePropertyChange(PARTICIPANTS_PROPERTY, null, null);
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
	
	public List getParticipantsSorted() {
		List list = new ArrayList(getParticipants());
		Collections.sort(list);
		return list;
	}
	
	public State getState(User user) {
		return participants.get(user);
	}
	
	public void setState(User user, State state) {
		State oldValue = participants.get(user);
		if (oldValue != state) {
			participants.put(user, state);
			pcs.firePropertyChange(STATE_PROPERTY, oldValue, state);
			pcs.firePropertyChange(PARTICIPANTS_PROPERTY, null, null);
		}
	}

	public boolean removeParticipant(User user){
		if (participants.containsKey(user)) {
			participants.remove(user);
			pcs.firePropertyChange(PARTICIPANTS_PROPERTY, user, null);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeAllParticipants() {
		participants.clear();
		return true;
	}
	
	public Meeting shallowCopy() {
		Meeting meeting = new Meeting(startDate, endDate, description, id, location);
		meeting.setOwner(new User(getOwner().getUsername()));
		
		for (User user : getParticipants()) {
			meeting.addParticipant(new User(user.getUsername()), getState(user));
		}
		
		return meeting;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (description != null) builder.append("Description: " + getDescription() + "\n"); else builder.append("Description is null\n");
		if (startDate != null) builder.append("Start: " + getStartDate() + "\n"); 			else builder.append("Start is null\n");
		if (endDate != null) builder.append("End: " + getEndDate() + "\n");					else builder.append("End is null\n");
		if (location != null) builder.append("Sted: " + getLocation() + "\n");				else builder.append("Location is null\n");
		if (owner != null) builder.append("Owner: " + getOwner().getName() + "\n");			else builder.append("Owner is null\n");
		
		builder.append("Participants: \n");
		for (User user : getParticipants()) {
			builder.append("\t" + user.getName() + ": " + (user) + "\n");
		}
		
		return builder.toString();
	}
	
}
