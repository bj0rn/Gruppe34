package no.ntnu.fp.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class NotificationPanel extends JPanel {

	private JLabel titleLabel;
	
	private JList notificationList;
	
	public NotificationPanel() {
		
		setLayout(new BorderLayout());
		
		titleLabel = new JLabel("Meldinger");		
		add(titleLabel, BorderLayout.NORTH);
		
		DefaultListModel model = new DefaultListModel();
		model.addElement("Rad 1");
		model.addElement("Rad 2");
		model.addElement("Rad 3");
		
		notificationList = new JList(model);
		add(notificationList, BorderLayout.CENTER);
		
		
	}
	
}
