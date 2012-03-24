package no.ntnu.fp.model;

import java.io.Serializable;

public class MeetingReplyNotification extends MeetingNotification implements Serializable{

	private static final long serialVersionUID = 7300644625481024863L;

	public MeetingReplyNotification(User user, Meeting meeting) {
		super(user, meeting);
	}
	
}
