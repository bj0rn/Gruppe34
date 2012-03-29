package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.fp.gui.timepicker.DateModel;

public abstract class Navigator extends JPanel implements ActionListener{
	public JButton previousButton, nextButton;
	private JLabel numberLabel, label;
	//private int value,max,min;
	
	protected DateModel model;
	
	public final static String VALUE_PROPERTY = "Value";
	
	//private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Navigator(DateModel model) { //String label, int current, int min, int max) {
		super();
		this.model = model;
		setLayout(new BorderLayout());
		//this.max=max;
		//this.min=min;
		numberLabel = new JLabel();
		nextButton = new JButton(">");
		previousButton = new JButton("<");
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
		
		//add(this.label = new JLabel(label), BorderLayout.NORTH);
		add(previousButton, BorderLayout.WEST);
		add(numberLabel, BorderLayout.CENTER);
		add(nextButton, BorderLayout.EAST);

		//setValue(current);
		update();
	}
	
	public void update() {
		numberLabel.setText(getText());
	}
	
	public abstract String getText(); 
	
	public void actionPerformed(ActionEvent e) {
		update();
	};
	
	@Override
	public String toString() {
		return "Navigator: " + label.getText();
	}
}
