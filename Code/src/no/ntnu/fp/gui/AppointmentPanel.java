package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppointmentPanel extends JPanel{
	private JLabel description, startTime, endTime, location;
	private JTextField descComp, startComp, endComp;
	private JComboBox locComp;
	//private Room[] rooms =
	
	private JButton save, delete;
	
	//få det ryddig i boksen
	protected GridBagLayout grid;
	protected GridBagConstraints constraints;
	
	public AppointmentPanel(){
		description = new JLabel("Beskrivelse");
		startTime = new JLabel("Start Tid");
		endTime = new JLabel("Slutt Tid");
		location = new JLabel("Sted");
		
		descComp = new JTextField(10);
		startComp = new JTextField(10);
		endComp = new JTextField(10);
		//locComp = new JComboBox
		
		save = new JButton("Lagre");
		delete = new JButton("Slett");
		
		grid = new GridBagLayout();
		constraints = new GridBagConstraints();
		
		setLayout(grid); // gjør at man faktisk endrer noe(det synes)
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(description, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(startTime, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(endTime, constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(location, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		add(descComp, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(startComp, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(endComp, constraints);
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(save, constraints);
		constraints.gridx = 1;
		constraints.gridy = 4;
		add(delete, constraints);
		
		
		
	}
	
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Frame"); //create a frame
		
		AppointmentPanel panel = new AppointmentPanel();
		frame.add(panel);
		//frame.setContentPane(contentPane)
		frame.setLocationRelativeTo(null); //center a frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //display the frame
		//frame.add(Ap)
		
		frame.pack();
	}

}
