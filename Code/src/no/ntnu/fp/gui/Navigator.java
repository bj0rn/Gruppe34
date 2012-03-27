package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Navigator extends JPanel implements ActionListener{
	public JButton previousButton, nextButton;
	private JLabel numberLabel, label;
	private int value,max,min;
	
	public final static String VALUE_PROPERTY = "Value";
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Navigator(String label, int current, int min, int max) {
		super();
		setLayout(new BorderLayout());
		this.max=max;
		this.min=min;
		numberLabel = new JLabel();
		nextButton = new JButton(">");
		previousButton = new JButton("<");
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
		
		add(this.label = new JLabel(label), BorderLayout.NORTH);
		add(previousButton, BorderLayout.WEST);
		add(numberLabel, BorderLayout.CENTER);
		add(nextButton, BorderLayout.EAST);

		setValue(current);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nextButton)){
			next();
		}else if(e.getSource().equals(previousButton)){
			previous();
		}
	}

	public void previous() {
		if(value>min){
			setValue(value-1);
		}else{
			value=min;
		}

	}

	public void next() {
		if(value<max){
			setValue(value+1);
		}else{
			value=max;
		}
	}

	public void setValue(int i) {
		pcs.firePropertyChange(VALUE_PROPERTY, this.value, i);
		this.value = i;
		numberLabel.setText(Integer.toString(i));
	}
	
	public int getValue(){
		return value;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l ) {
		pcs.addPropertyChangeListener(l);
	}
	
	@Override
	public String toString() {
		return "Navigator: " + label.getText();
	}
}
