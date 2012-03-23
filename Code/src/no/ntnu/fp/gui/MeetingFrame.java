package no.ntnu.fp.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Meeting;


public class MeetingFrame extends JPanel implements PropertyChangeListener, ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
    private static final int SPACING = 5;
    private static final Dimension LABEL_SIZE = new Dimension(100, 20);
    private static final Dimension TEXT_INPUT_SIZE = new Dimension(260, 20);
    
    private JTextField description = new JTextField(20);
    private JTextField startField = new JTextField(10);
    private JTextField endField = new JTextField(10);
    private JList participantList = new JList();
    private JButton addParticipantButton = new JButton(new addParticipantAction("Legg til Deltaker"));
    private JTextField locationField = new JTextField(20);
    private JButton reserveRoomListButton = new JButton(new roomListAction("Rom"));
    private JButton saveButton = new JButton(new saveAction("Lagre"));
    private JButton cancelButton = new JButton(new cancelAction("Avbryt"));
    
    private Meeting model;
    
    public MeetingFrame() {
    	participantList = new JList();
    	participantList.setPreferredSize(new Dimension(100, 150));
    	
    	JLabel labelMeeting = new JLabel("Møte");
        JLabel labelDescription = new JLabel("Beskrivelse:");
        JLabel labelStart = new JLabel("Start:");
        JLabel labelEnd = new JLabel("Slutt:");
        JLabel labelParticipants = new JLabel("Deltakere:");
        JLabel labelPlace = new JLabel("Sted:");
        
        add(labelMeeting);
        add(labelDescription);
        add(description);
        add(labelStart);
        add(startField);
        add(labelEnd);
        add(endField);
        add(labelParticipants);
        add(participantList);
        add(addParticipantButton);
        add(labelPlace);
        add(locationField);
        add(reserveRoomListButton);
        add(saveButton);
        add(cancelButton);
        

       
        for (Component c : getComponents()) {
            if (c instanceof JLabel) c.setPreferredSize(LABEL_SIZE);
            if (c instanceof JComboBox) c.setPreferredSize(LABEL_SIZE);
            if (c instanceof JTextField) c.setPreferredSize(TEXT_INPUT_SIZE);
        }
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        layout.putConstraint(SpringLayout.WEST, labelMeeting, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelMeeting, SPACING, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, labelDescription, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelDescription, SPACING, SpringLayout.SOUTH, labelMeeting);
        layout.putConstraint(SpringLayout.WEST, description, SPACING, SpringLayout.EAST, labelDescription);
        layout.putConstraint(SpringLayout.NORTH, description, SPACING, SpringLayout.NORTH, labelDescription);
        
        layout.putConstraint(SpringLayout.WEST, labelStart, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelStart, SPACING, SpringLayout.SOUTH, labelDescription);
        layout.putConstraint(SpringLayout.WEST, startField, SPACING, SpringLayout.EAST, labelStart);
        layout.putConstraint(SpringLayout.NORTH, startField, SPACING, SpringLayout.NORTH, labelStart);
        
        layout.putConstraint(SpringLayout.WEST, labelEnd, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelEnd, SPACING, SpringLayout.SOUTH, labelStart);
        layout.putConstraint(SpringLayout.WEST, endField, SPACING, SpringLayout.EAST, labelEnd);
        layout.putConstraint(SpringLayout.NORTH, endField, SPACING, SpringLayout.NORTH, labelEnd);
        
        layout.putConstraint(SpringLayout.WEST, labelParticipants, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelParticipants, SPACING, SpringLayout.SOUTH, labelEnd);
        
        layout.putConstraint(SpringLayout.WEST, participantList, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, participantList, SPACING, SpringLayout.SOUTH, labelParticipants);
        
        layout.putConstraint(SpringLayout.WEST, addParticipantButton, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, addParticipantButton, SPACING, SpringLayout.SOUTH, participantList);
        
        layout.putConstraint(SpringLayout.WEST, labelPlace, SPACING, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelPlace, SPACING, SpringLayout.SOUTH, addParticipantButton);
        layout.putConstraint(SpringLayout.WEST, locationField, SPACING, SpringLayout.EAST, labelPlace);
        layout.putConstraint(SpringLayout.NORTH, locationField, SPACING, SpringLayout.NORTH, labelPlace);
        layout.putConstraint(SpringLayout.WEST, reserveRoomListButton, SPACING, SpringLayout.EAST, locationField);
        layout.putConstraint(SpringLayout.NORTH, reserveRoomListButton, -4, SpringLayout.NORTH, locationField);

        
        layout.putConstraint(SpringLayout.WEST, saveButton, 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 30, SpringLayout.SOUTH, labelPlace);
        layout.putConstraint(SpringLayout.WEST, cancelButton, SPACING, SpringLayout.EAST, saveButton);
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, saveButton);
        
        
        setPreferredSize(new Dimension(410, 410));
        
        participantList.addListSelectionListener(this);
        participantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
    }
    
    //Action for Legg til deltaker
    private class addParticipantAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public addParticipantAction(String text) {
        	super(text, null); 
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("Legg til deltaker");
        }
    }
    
    //Action for Rom reservering
    private class roomListAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public roomListAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("Reserver rom");
        }
    }
    
    //Action for lagring av skjema
    private class saveAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public saveAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("lagre");
        }
    }
    
    //Action for å avbryte skjema
    private class cancelAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public cancelAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("Avbryt");
        }
    }

    public void setModel(Meeting model)  {
    	if (model != null) {
    		if (this.model != null) {
    			this.model.removePropertyChangeListener(this);
    		}
    		
    		this.model = model;
    		this.model.addPropertyChangeListener(this);
    		updatePanel();
    	}
    }
    
    @SuppressWarnings("deprecation")
	public void updatePanel() {
    	description.setText(model.getDescription());
    	startField.setText(model.getStartDate().toGMTString());
    	endField.setText(model.getEndDate().toGMTString());
    	participantList.setListData(model.getParticipants());
    	locationField.setText(model.getLocation().toString());
    }
    
    
    
    @Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
    	System.out.println("Event!");
        final String name = event.getPropertyName();
        if (name == CalendarEntry.DESC_PROPERTY) {
        	
        }
        else if (name == CalendarEntry.START_PROPERTY) {
        	
        }
         
        else if (name == CalendarEntry.END_PROPERTY) {
            endField.setText((String) event.getNewValue());
         }
        else if (name == CalendarEntry.PARTICIPANTS_PROPERTY) {
        	
         }
        else if (name == CalendarEntry.LOC_PROPERTY) {
        	
        }
	}
    
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Møte");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MeetingFrame());
        try {
            // 1.06+
            frame.setLocationByPlatform(true);
            frame.setMinimumSize(frame.getSize());
        } catch(Throwable ignoreAndContinue) {
        }
        frame.pack();
        frame.setVisible(true);
    }
}
