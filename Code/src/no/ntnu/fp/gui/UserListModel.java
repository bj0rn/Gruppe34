package no.ntnu.fp.gui;

import javax.swing.AbstractListModel;

import no.ntnu.fp.net.network.client.CommunicationController;

public class UserListModel extends AbstractListModel {
	
	private CommunicationController communication = CommunicationController.getInstance(); 
		
	@Override
	public int getSize() {
		return communication.getListOfUsers().size();
	}

	@Override
	public Object getElementAt(int index) {
		return communication.getListOfUsers().get(index);
	}

}
