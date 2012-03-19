package no.ntnu.fp.model;

import java.util.List;


public class Meeting extends CalendarEntry{
	private ModelChangeListener modelChangeListener;
	private List<Participant> participants;
	
	public Meeting(String description){
		
	}
	
	public void addParticipant(Participant participant){
		participants.add(participant);
	}

	public boolean removeParticipant(Participant participant){
		return participants.remove(participant);
	}

}
