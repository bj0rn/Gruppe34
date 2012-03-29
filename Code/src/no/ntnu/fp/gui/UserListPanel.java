package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.CommunicationController;

public class UserListPanel extends JFrame implements ListSelectionListener {

	public static final String FRAME_TITLE = "Brukere";
	public static final String OK_BUTTON_LABEL = "Ok";
	public static final String CANCEL_BUTTON_LABEL = "Cancel";
	
	private ListModel listModel;
	private ListSelectionModel selectionModel;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private CommunicationController communication = CommunicationController.getInstance();
	
	private JList list;
	
	public UserListPanel() {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel(FRAME_TITLE);
		titleLabel.setFont(StylingDefinition.FRAME_TITLE_FONT);
		titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(titleLabel, BorderLayout.NORTH);
		
		listModel = new UserListModel();
		list = new JList(listModel);
		list.setCellRenderer(new UserListCellRenderer());
		selectionModel = list.getSelectionModel();
		//selectionModel = new UserListSelectionModel();
		//list.setSelectionModel(selectionModel);
		selectionModel.addListSelectionListener(this);
		panel.add(list, BorderLayout.CENTER);
		initSelection();
		
		JPanel buttons = new JPanel();
		
		okButton = new JButton(OK_BUTTON_LABEL);
		okButton.addActionListener(new OkButtonAction());
		buttons.add(okButton);
		
		cancelButton = new JButton(CANCEL_BUTTON_LABEL);
		cancelButton.addActionListener(new CancelButtonAction());
		buttons.add(cancelButton);
		
		panel.add(buttons, BorderLayout.SOUTH);
		
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
			User user = (User)listModel.getElementAt(index);
			if (selectionModel.isSelectedIndex(index)) {
				communication.addSelectedUser(user);
			} else {
				communication.removeSelectedUser(user);
				System.out.println(user.getName());
			}
		}
	}

	/*private class UserListSelectionModel extends DefaultListSelectionModel {
		
		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (super.isSelectedIndex(index0)) {
				super.removeIndexInterval(index0, index1);
			} else {
				super.addSelectionInterval(index0, index1);
			}
		}
	}*/
	
	private class OkButtonAction extends AbstractAction {

		private List<User> cachedUsers = new ArrayList<User>();
		
		public OkButtonAction() {
			for (User user : communication.getSelectedUsers()) {
				cachedUsers.add(user);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for (User user : communication.getSelectedUsers()) {
				if (!cachedUsers.contains(user)) {
					System.out.println(user.getUsername());
					User fullUser = communication.getFullUser(user.getUsername());
					communication.addFullUser(fullUser);
				}
			}
			
			for (User user : cachedUsers) {
				System.out.println("user:"+user.getName());
				if (!communication.getSelectedUsers().contains(user)) {
					System.out.println("not:"+user.getName());
					communication.cancelView(user.getUsername());
				}
			}
			dispose();
		}
		
	}
	
	private class CancelButtonAction extends AbstractAction {

		private List<User> cachedUsers = new ArrayList<User>();
		
		public CancelButtonAction() {
			for (User user : communication.getSelectedUsers()) {
				cachedUsers.add(user);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			for (User user : communication.getSelectedUsers()) {
				System.out.println(user.getName());
				if (!cachedUsers.contains(user)) {
					System.out.println(user.getName());
					communication.removeSelectedUser(user);
				}
			}
			dispose();
			
		}
	}
}