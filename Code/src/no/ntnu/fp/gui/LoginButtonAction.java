package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class LoginButtonAction extends AbstractAction {

	private LoginFrame frame;
	private Authenticate auth;
	
	public LoginButtonAction(LoginFrame frame, Authenticate auth) {
		this.frame = frame;
		this.auth = auth;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		CommunicationController c = CommunicationController.getInstance();
		boolean authenticated = c.authenticate(auth);
		
		if (authenticated) {
			new ApplicationFrame(auth.getUsername());
			frame.dispose();
		} else {
			//frame.notifyNotCorrect
		}
		
	}

}
