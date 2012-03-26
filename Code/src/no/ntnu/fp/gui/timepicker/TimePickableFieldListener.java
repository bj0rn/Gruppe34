package no.ntnu.fp.gui.timepicker;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimePickableFieldListener implements FocusListener {

	private JPanel glassPane;
	private JTextField field;
	private DateTimePicker picker;
	
	public TimePickableFieldListener(JTextField field, JFrame frame) {
		this.field = field;
		this.glassPane = (JPanel)frame.getGlassPane();
		this.glassPane.setLayout(null);
		picker = new DateTimePicker(field);
		picker.setVisible(false);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		this.glassPane.setVisible(true);
		
		glassPane.add(picker);
		
		int x = field.getBounds().x;
		int y = field.getBounds().y + field.getBounds().height;
		int width = picker.getMinimumSize().width;
		int height = picker.getMinimumSize().height;
		
		picker.setBounds(x, y, width, height);
		picker.setVisible(true);
		
		glassPane.repaint();
		
	}

	@Override
	public void focusLost(FocusEvent e) {}
	
}