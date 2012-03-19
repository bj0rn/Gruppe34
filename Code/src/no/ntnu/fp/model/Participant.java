package no.ntnu.fp.model;


public class Participant {
	private ModelChangeListener modelChangeListener;
	private State state;
	private Meeting meeting;
	private User user;
	
	public Participant(User user, Meeting meeting){
		this.user = user;
		this.meeting = meeting;
	}

}
