package no.ntnu.fp.model;

import java.io.Serializable;

public class MeetingInviteNotification extends MeetingNotification implements Serializable{

	private static final long serialVersionUID = 5567053784773832883L;

	public MeetingInviteNotification(User user, Meeting meeting) {
		super(user, meeting);
	}
	
}
