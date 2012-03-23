package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class LoginButtonAction extends AbstractAction {

	private Authenticate auth;
	
	public LoginButtonAction(Authenticate auth) {
		this.auth = auth;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommunicationController c = CommunicationController.getInstance();
		
		String username = auth.getUsername();
		String password = auth.getPassword();
		
		boolean authenticated = c.authenticate(username, password);
		
		if (authenticated) {
			System.out.println(username);
			System.out.println(password);
			System.out.println("yeah");
		} else {
			System.out.println(username);
			System.out.println(password);
			System.out.println("bah");
		}
		
	}

}
