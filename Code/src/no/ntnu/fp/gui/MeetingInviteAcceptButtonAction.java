package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.net.network.client.CommunicationController;

public class MeetingInviteAcceptButtonAction extends AbstractAction {

	private Meeting meeting;
	private User user;
	
	public MeetingInviteAcceptButtonAction(Meeting meeting, User user) {
		this.meeting = meeting;
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		State state = State.Accepted;
		
		CommunicationController c = CommunicationController.getInstance();
		c.dispatchMeetingReply(user, meeting, state);
		
		meeting.setState(user, state);
	}

}
