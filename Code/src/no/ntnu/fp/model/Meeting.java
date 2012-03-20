package no.ntnu.fp.model;

import java.util.Date;
import java.util.List;


public class Meeting extends CalendarEntry{
	
	
	private ModelChangeListener modelChangeListener;
	private List<Participant> participants;
	
	public Meeting(String description){
		super(description);
	}
	
	public Meeting(Date start, Date end, String description) {
		super(start, end, description);
	}
	
	public void addParticipant(Participant participant){
		participants.add(participant);
	}

	public boolean removeParticipant(Participant participant){
		return participants.remove(participant);
	}

}
