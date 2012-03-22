package no.ntnu.fp.model;

import java.io.Serializable;


public class MeetingNotification extends Notification implements Serializable {
	
	private static final long serialVersionUID = 388867251492186805L;
	
	private Meeting meeting;
	private User user;
	
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	
	public Meeting getMeeting() {
		return meeting;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}
