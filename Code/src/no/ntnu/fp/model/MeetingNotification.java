package no.ntnu.fp.model;

import java.io.Serializable;


public class MeetingNotification extends Notification implements Serializable {
	
	private static final long serialVersionUID = 388867251492186805L;
	
	private Participant participant;
	
	public void setParticipant(Participant participant){
		this.participant = participant;
	}

}
