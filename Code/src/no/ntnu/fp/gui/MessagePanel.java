package no.ntnu.fp.gui;

import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MessagePanel extends JPanel {
	JLabel headerLabel;
	JList messageList;

	public MessagePanel() {
		add(headerLabel = new JLabel("Meldinger"));
		add(messageList = new JList());
	}
	
}
