package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;

public class MeetingReplyEditButtonAction extends AbstractAction {

	public Meeting meeting;
	public User user;
	
	public MeetingReplyEditButtonAction(Meeting meeting, User user) {
		this.meeting = meeting;
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new MeetingFrame(meeting);
	}

}
