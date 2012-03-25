package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class MeetingInviteRejectButtonAction extends AbstractAction {

	private Meeting meeting;
	private User user;
	
	public MeetingInviteRejectButtonAction(Meeting meeting, User user) {
		this.meeting = meeting;
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommunicationController c = CommunicationController.getInstance();
		c.dispatchMeetingReply(user, meeting, State.Rejected);
	}

}
