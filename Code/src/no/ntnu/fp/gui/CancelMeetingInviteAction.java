package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;

public class CancelMeetingInviteAction extends AbstractAction {

	private MeetingInviteFrame model;
	private State orginalState;
	
	public CancelMeetingInviteAction(MeetingInviteFrame model) {
		this.model = model;
		User user = model.getUser();
		orginalState = model.getModel().getState(user);
		System.out.println(orginalState);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Meeting meeting = model.getModel();
		User user = model.getUser();
		State state = meeting.getState(user);
		
		
		if (state != orginalState) {
			meeting.setState(user, orginalState);
		}
		
		System.out.println(meeting);
		
		model.dispose();
	}

}