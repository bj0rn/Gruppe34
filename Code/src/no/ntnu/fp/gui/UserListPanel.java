package no.ntnu.fp.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class UserListPanel extends JFrame implements ListSelectionListener {

	public static final String FRAME_TITLE = "Brukere";
	
	private ListModel listModel;
	private ListSelectionModel selectionModel;
	
	private CommunicationController communication = CommunicationController.getInstance();
	
	private JList list;
	
	public UserListPanel() {
		
		JPanel panel = new JPanel();
		
		listModel = new UserListModel();
		list = new JList(listModel);
		list.setCellRenderer(new UserListCellRenderer());
		selectionModel = new UserListSelectionModel();
		list.setSelectionModel(selectionModel);
		selectionModel.addListSelectionListener(this);
		panel.add(list);
		
		initSelection();
		
		setContentPane(panel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	private void initSelection() {
		List<User> users = communication.getSelectedUsers();
		
		for(int i=0; i<listModel.getSize(); i++) {
			User user = (User)listModel.getElementAt(i);
			if (users.contains(user)) {
				list.setSelectedIndex(i);
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) { 
			int index = selectionModel.getAnchorSelectionIndex();
			
			if (selectionModel.isSelectedIndex(index)) {
				communication.addSelectedUser((User)listModel.getElementAt(index));
			} else {
				communication.removeSelectedUser((User)listModel.getElementAt(index));
			}
		}
	}

	private class UserListSelectionModel extends DefaultListSelectionModel {
		
		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (super.isSelectedIndex(index0)) {
				super.removeIndexInterval(index0, index1);
			} else {
				super.addSelectionInterval(index0, index1);
			}
		}
	}
}