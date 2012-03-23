package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class OkMeetingInviteAction extends AbstractAction {

	private MeetingInviteFrame model;
	private State orginalState;
	
	
	public OkMeetingInviteAction(MeetingInviteFrame model) {
		this.model = model;
		User user = model.getUser();
		orginalState = model.getModel().getState(user);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Meeting meeting = model.getModel();
		User user = model.getUser();
		State state = meeting.getState(user);
		
		if (state != orginalState) {
			CommunicationController c = CommunicationController.getInstance();
			c.dispatchMeetingReply(user, meeting, state);
		}
		
		model.dispose();
		
	}

}
