package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Navigator extends JPanel implements ActionListener{
	public JButton previousButton, nextButton;
	private JLabel numberLabel;
	private int i,max,min;
	
	public Navigator(int i, int min, int max) {
		super();
		this.max=max;
		this.min=min;
		numberLabel = new JLabel();
		nextButton = new JButton(">");
		previousButton = new JButton("<");
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
		
		add(previousButton);
		add(numberLabel);
		add(nextButton);

		setNumber(i);
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
		if(i>min){
			i--;
			setNumber(i);
		}else{
			i=min;
		}

	}

	public void next() {
		if(i<max){
			i++;
			setNumber(i);
		}else{
			i=max;
		}
	}

	public void setNumber(int i) {
		this.i = i;
		numberLabel.setText(Integer.toString(i));
	}
}
