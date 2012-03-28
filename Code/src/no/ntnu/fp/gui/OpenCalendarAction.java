package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class OpenCalendarAction extends AbstractAction  {

	private ApplicationFrame panel;
	
	public OpenCalendarAction(ApplicationFrame panel) {
		this.panel = panel;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new UserListPanel();
	}

}
