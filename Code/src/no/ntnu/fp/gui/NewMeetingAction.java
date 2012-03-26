package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.User;

public class NewMeetingAction extends AbstractAction {

	private ApplicationFrame panel;
	private User user;
	
	public NewMeetingAction(ApplicationFrame panel, User user) {
		this.panel = panel;
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new MeetingFrame(user);
	}
	
}
