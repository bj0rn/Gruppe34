package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;

public class MeetingInviteShowButtonAction extends AbstractAction {

	private Meeting meeting;
	private User user;

	public MeetingInviteShowButtonAction(Meeting meeting, User user) {
		this.meeting = meeting;
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new MeetingInviteFrame(meeting);
	}

}
