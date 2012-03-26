package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.util.GridBagHelper;
import no.ntnu.fp.util.TimeLord;

public class AppointmentPanel extends JPanel implements PropertyChangeListener {
	private JLabel appointment, description, startTime, endTime, location;
	private JTextField descComp, startComp, endComp;
	private JTextField locComp;
	//private JComboBox locComp;
	//private Room[] rooms =
	private PlacePickerPanel plPickPanel;
	
	private JButton save, delete;
	
	//få det ryddig i boksen
	protected GridBagLayout grid;
	protected GridBagConstraints constraints;
	
	private Appointment model;
	
	public AppointmentPanel(Appointment appmnt) {
		this();
		this.model = appmnt;
	}
	public AppointmentPanel() {
		appointment = new JLabel("Avtale");
		description = new JLabel("Beskrivelse");
		startTime = new JLabel("Starttid");
		endTime = new JLabel("Sluttid");
		location = new JLabel("Sted");
		
		plPickPanel = new PlacePickerPanel();
		plPickPanel.addPropertyChangeListener(this);
		
		descComp = new JTextField(10);
		startComp = new JTextField(10);
		endComp = new JTextField(10);
		locComp = new JTextField(10);
		//locComp = new JComboBox();
		
		save = new JButton("Lagre");
		delete = new JButton("Slett");
		
		grid = new GridBagLayout();
		constraints = new GridBagConstraints();
		
		setLayout(grid); // gjør at man faktisk endrer noe(det synes)
		
		constraints.gridwidth = constraints.RELATIVE;
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
		constraints.gridx = 3;
		constraints.gridy = 1;
		add(descComp, constraints);
		constraints.gridx = 3;
		constraints.gridy = 2;
		add(startComp, constraints);
		constraints.gridx = 3;
		constraints.gridy = 3;
		add(endComp, constraints);
		constraints.gridx = 3;
		constraints.gridy = 4;
		add(locComp, constraints);
		constraints.gridx = 0;
		constraints.gridy = 6;
		add(save, constraints);
		constraints.gridx = 3;
		constraints.gridy = 6;
		add(delete, constraints);
		
		constraints.gridwidth = constraints.REMAINDER;
		add(plPickPanel, GridBagHelper.setConstraints(constraints, 0, 5));
		
		descComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setDescription(descComp.getText());
			}
		});
		
		startComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setStartDate(TimeLord.changeDateToJava(startComp.getText()));
			}
		});
		startComp.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("DP");
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		endComp.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				model.setEndDate(TimeLord.changeDateToJava(endComp.getText()));
			}
		}); 
		endComp.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("I!");
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		locComp.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//uh, okay, so let's, ah -- uh, get that list of rooms/places, right?
				//launch placepicker
			}
		});
		//private JButton save, delete;
		this.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//button is clicked, run code that will save the model
			}
		});
		this.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//button is clicked DELETE EVERYTHING.
				//wait _where_ are we keeping the code to send the DB-req to delete something?
			}
		});
	}
	
	 private void updatePanel() {
	       if (model != null) {
	    	   descComp.setText(model.getDescription());
	    	   startComp.setText(TimeLord.changeDateToSQL(model.getStartDate()));
	    	   endComp.setText(TimeLord.changeDateToSQL(model.getEndDate()));
	    	   locComp.setText(model.getLocation().getID() + "");
	    	   plPickPanel.updatePanel();
	       }
	    }
	 
	   public void setModel(Appointment app) {
   		if (app != null) {
   			if (model != null) {
   				model.removePropertyChangeListener(this);
   			}
   			model = app;
   			model.addPropertyChangeListener(this);
   			plPickPanel.setLocation(model.getLocation());
   			
   			updatePanel();
   		}
    }
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Frame"); //create a frame
		
		AppointmentPanel panel = new AppointmentPanel();
		frame.add(panel); //adder alt i konstruktøren
		
		Appointment app = new Appointment(new Date(0, 0, 0), new Date(2012, 05, 03),
				"Kill the batman", 35);
		app.setLocation(new Place(33, "Gotham City"));
		panel.setModel(app);
		

		
		
		frame.setLocationRelativeTo(null); //center a frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //display the frame
		
		frame.pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("d");
		if(evt.getPropertyName() == Appointment.DESC_PROPERTY) {
				descComp.setText(model.getDescription());
		}
		if(evt.getPropertyName() == Appointment.END_PROPERTY){
			endComp.setText(TimeLord.formatDate(model.getEndDate()));
		}
		if(evt.getPropertyName() == Appointment.START_PROPERTY){
			startComp.setText(TimeLord.formatDate(model.getStartDate()));
		}
		if(evt.getPropertyName() == Appointment.LOC_PROPERTY){
			locComp.setText(model.getLocation().getDescription());
			System.out.println();
		}
		if (evt.getPropertyName() == PlacePickerPanel.LOCATIONC_PROPERTY) {
			model.setLocation((Location) evt.getNewValue());
			System.out.println("OK!");
		}
	}

}