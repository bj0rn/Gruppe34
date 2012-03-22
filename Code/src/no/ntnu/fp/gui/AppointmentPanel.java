package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Person;

public class AppointmentPanel extends JPanel implements PropertyChangeListener {
	private JLabel appointment, description, startTime, endTime, location;
	private JTextField descComp, startComp, endComp;
	private JComboBox locComp;
	//private Room[] rooms =
	
	private JButton save, delete;
	
	//f� det ryddig i boksen
	protected GridBagLayout grid;
	protected GridBagConstraints constraints;
	
	private Appointment model;
	
	public AppointmentPanel(){
		appointment = new JLabel("Avtale");
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
		
		setLayout(grid); // gj�r at man faktisk endrer noe(det synes)
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(appointment, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(description, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(startTime, constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(endTime, constraints);
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(location, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(descComp, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(startComp, constraints);
		constraints.gridx = 1;
		constraints.gridy = 3;
		add(endComp, constraints);
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(save, constraints);
		constraints.gridx = 1;
		constraints.gridy = 5;
		add(delete, constraints);
		
		descComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setDescription(descComp.getText());
			}
		});
		
		startComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setStartDate(startComp.getStartDate());
			}
		});
		
		endComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setEndDate(endComp.getEndDate());
			}
		}); 
		
	}
	
	 private void updatePanel() {
	       if (model != null) {
	    	   descComp.setText(model.getDescription());
	    	   startComp.setText(model.getStartDate().toGMTString());
	    	   endComp.setText(model.getEndDate().toGMTString());
	       }
	    }
	 
	   public void setModel(Appointment app) {
   		if (app != null) {
   			if (model != null)
   				model.removePropertyChangeListener(this);
   			model = app;
   			model.addPropertyChangeListener(this);
   			updatePanel();
   		}
    }
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Frame"); //create a frame
		
		AppointmentPanel panel = new AppointmentPanel();
		frame.add(panel); //adder alt i konstrukt�ren
		
		Appointment app = new Appointment("test");
		app.setDate(new Date(2012,2,1,12,0,0), new Date(2012,2,1,12,0,30));
		
		panel.setModel(app);
		
		app.setDescription("he");
		
		frame.setLocationRelativeTo(null); //center a frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //display the frame
		
		frame.pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}