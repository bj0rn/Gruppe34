package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Appointment;

public class NewAppointmentAction extends AbstractAction {

	private ApplicationFrame panel;
	
	public NewAppointmentAction(ApplicationFrame panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		new AppointmentPanel(new Appointment("derp"));
	}

}
