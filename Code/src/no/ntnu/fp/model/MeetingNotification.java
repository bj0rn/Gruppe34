package no.ntnu.fp.model;


public class MeetingNotification extends Notification {
	private Participant participant;
	
	public void setParticipant(Participant participant){
		this.participant = participant;
	}

}
