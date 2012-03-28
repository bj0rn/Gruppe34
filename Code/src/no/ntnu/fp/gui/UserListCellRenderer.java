package no.ntnu.fp.gui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import no.ntnu.fp.model.User;

public class UserListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
		
		JPanel panel = new JPanel();
		
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(isSelected);
		panel.add(checkBox);
		
		User user = (User)list.getModel().getElementAt(index);
		
		JLabel label = new JLabel(user.getName());
		panel.add(label);
		
		return panel;
	}
	
}
