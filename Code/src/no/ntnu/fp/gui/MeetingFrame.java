package no.ntnu.fp.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.gui.timepicker.*;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.util.TimeLord;


public class MeetingFrame extends JFrame implements PropertyChangeListener {
	
	private static final long serialVersionUID = 1L;
    private static final int SPACING = 5;
    private static final Dimension LABEL_SIZE = new Dimension(100, 20);
    private static final Dimension TEXT_INPUT_SIZE = new Dimension(260, 20);
    
    private JTextField description = new JTextField(20);
    private JTextField startField = new JTextField(10);
    private JTextField endField = new JTextField(10);
    private JEditableList participantList;
    private JButton addParticipantButton = new JButton(new addParticipantAction("Legg til Deltaker"));
    private JTextField locationField = new JTextField(20);
    private JButton reserveRoomListButton = new JButton(new roomListAction("Rom"));
    private JButton saveButton = new JButton(new saveAction("Lagre"));
    private JButton cancelButton = new JButton(new cancelAction("Avbryt"));
    
    private Meeting model;
    private ParticipantListModel listModel;
    
    public MeetingFrame(User user) {    	
    	this(user, new Meeting());
    }
    
    public MeetingFrame(User user, Meeting model) {
    	
    	JPanel panel = new JPanel();
    	
    	
    	
    	listModel = new ParticipantListModel(model);
    	participantList = new JEditableList(listModel, new ParticipantListCellRenderer());
    	participantList.setPreferredSize(new Dimension(410, 150));
    	
    	JLabel labelMeeting = new JLabel("M�te");
        JLabel labelDescription = new JLabel("Beskrivelse:");
        JLabel labelStart = new JLabel("Start:");
        JLabel labelEnd = new JLabel("Slutt:");
        JLabel labelParticipants = new JLabel("Deltakere:");
        JLabel labelPlace = new JLabel("Sted:");
        
        panel.add(labelMeeting);
        panel.add(labelDescription);
        panel.add(description);
        panel.add(labelStart);
        panel.add(startField);
        panel.add(labelEnd);
        panel.add(endField);
        panel.add(labelParticipants);
        panel.add(participantList);
        panel.add(addParticipantButton);
        panel.add(labelPlace);
        panel.add(locationField);
        panel.add(reserveRoomListButton);
        panel.add(saveButton);
        panel.add(cancelButton);
        
        description.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		getModel().setDescription(description.getText());
        	}
		});
        
        startField.getDocument().addDocumentListener(new DocumentListener() {
			
        	public void changedUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				if (getModel() != null) {
					getModel().setStartDate(TimeLord.parseDate(startField.getText()));
				}
			}
		});
        
        endField.getDocument().addDocumentListener(new DocumentListener() {
        	
        	public void changedUpdate(DocumentEvent e) {}
        	public void removeUpdate(DocumentEvent e) {}
			public void insertUpdate(DocumentEvent e) {
				if (getModel() != null) {
					getModel().setEndDate(TimeLord.parseDate(startField.getText()));
				}
			}
        });
        
        startField.addFocusListener(new TimePickableFieldListener(startField, this));
        endField.addFocusListener(new TimePickableFieldListener(endField, this));

       
        for (Component c : getComponents()) {
            if (c instanceof JLabel) c.setPreferredSize(LABEL_SIZE);
            if (c instanceof JComboBox) c.setPreferredSize(LABEL_SIZE);
            if (c instanceof JTextField) c.setPreferredSize(TEXT_INPUT_SIZE);
        }
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        layout.putConstraint(SpringLayout.WEST, labelMeeting, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelMeeting, SPACING, SpringLayout.NORTH, panel);
        
        layout.putConstraint(SpringLayout.WEST, labelDescription, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelDescription, SPACING, SpringLayout.SOUTH, labelMeeting);
        layout.putConstraint(SpringLayout.WEST, description, SPACING, SpringLayout.EAST, labelDescription);
        layout.putConstraint(SpringLayout.NORTH, description, SPACING, SpringLayout.NORTH, labelDescription);
        
        layout.putConstraint(SpringLayout.WEST, labelStart, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelStart, SPACING, SpringLayout.SOUTH, labelDescription);
        layout.putConstraint(SpringLayout.WEST, startField, SPACING, SpringLayout.EAST, labelStart);
        layout.putConstraint(SpringLayout.NORTH, startField, SPACING, SpringLayout.NORTH, labelStart);
        
        layout.putConstraint(SpringLayout.WEST, labelEnd, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelEnd, SPACING, SpringLayout.SOUTH, labelStart);
        layout.putConstraint(SpringLayout.WEST, endField, SPACING, SpringLayout.EAST, labelEnd);
        layout.putConstraint(SpringLayout.NORTH, endField, SPACING, SpringLayout.NORTH, labelEnd);
        
        layout.putConstraint(SpringLayout.WEST, labelParticipants, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelParticipants, SPACING, SpringLayout.SOUTH, labelEnd);
        
        layout.putConstraint(SpringLayout.WEST, participantList, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, participantList, SPACING, SpringLayout.SOUTH, labelParticipants);
        
        layout.putConstraint(SpringLayout.WEST, addParticipantButton, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addParticipantButton, SPACING, SpringLayout.SOUTH, participantList);
        
        layout.putConstraint(SpringLayout.WEST, labelPlace, SPACING, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, labelPlace, SPACING, SpringLayout.SOUTH, addParticipantButton);
        layout.putConstraint(SpringLayout.WEST, locationField, SPACING, SpringLayout.EAST, labelPlace);
        layout.putConstraint(SpringLayout.NORTH, locationField, SPACING, SpringLayout.NORTH, labelPlace);
        layout.putConstraint(SpringLayout.WEST, reserveRoomListButton, SPACING, SpringLayout.EAST, locationField);
        layout.putConstraint(SpringLayout.NORTH, reserveRoomListButton, -4, SpringLayout.NORTH, locationField);
        
        layout.putConstraint(SpringLayout.WEST, saveButton, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 30, SpringLayout.SOUTH, labelPlace);
        layout.putConstraint(SpringLayout.WEST, cancelButton, SPACING, SpringLayout.EAST, saveButton);
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, saveButton);
        
        setModel(model);
        
        panel.setPreferredSize(new Dimension(410, 410));
        setPreferredSize(new Dimension(410, 410));
        
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
        
    }
    
    public Meeting getModel() {
    	return model;
    }
    
    //Action for Legg til deltaker
    private class addParticipantAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public addParticipantAction(String text) {
        	super(text, null); 
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	new ListRenderingFrame(model);
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
        	CommunicationController c = CommunicationController.getInstance();
        	c.saveMeeting(model.clone());
        }
    }
    
    //Action for � avbryte skjema
    private class cancelAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public cancelAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	dispose();
        }
    }
    
    private class RemoveParticipant extends AbstractAction {
    	
    	private User user;
    	
    	public RemoveParticipant(User user) {
    		this.user = user;
    	}
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		model.removeParticipant(user);
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
    	startField.setText(TimeLord.formatDate(model.getStartDate()));
    	endField.setText(TimeLord.formatDate(model.getEndDate()));
    	//participantList.setListData(model.getParticipants());
    	updateLocation();
    }
    
    private void updateLocation() {
    	Location location = model.getLocation(); 
    	if (location != null) {
    		locationField.setText(model.getLocation().toString());
    	} else {
    		locationField.setText("");
    	}
    }
    
    
    @Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
        final String name = event.getPropertyName();
        if (name == CalendarEntry.DESC_PROPERTY) {
        	description.setText(model.getDescription());
        }
        else if (name == CalendarEntry.START_PROPERTY) {}
         
        else if (name == CalendarEntry.END_PROPERTY) {
            endField.setText((String) event.getNewValue());
         }
        else if (name == Meeting.PARTICIPANTS_PROPERTY) {}
        else if (name == CalendarEntry.LOC_PROPERTY) {
        	locationField.setText(model.getLocation().toString());
        }
	}
	
	class ParticipantListCellRenderer implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JPanel panel = new JPanel();
			
			User participant = (User)listModel.getElementAt(index);
			
			panel.add(new JLabel(participant.getName()));
			panel.add(new JLabel("Status"));
			JButton button = new JButton("Fjern");
			button.addActionListener(new RemoveParticipant(participant));
			panel.add(button);
			
			return panel;
		}
	}
    
    public static void main(String[] args) {
    	
    	User user = new User("havard");
		user.setName("Håvard Wormdal Høiby");
		
		User p1 = new User("bjorn");
		p1.setName("Bjørn Åge Tungesvik");
		
		User p2 = new User("odd");
		p2.setName("Odd Magnus Trondrud");
		
		User p3 = new User("andy");
		p3.setName("Andre Philipp");
		
		User p4 = new User("eivind");
		p4.setName("Eivind Kvissel");
		
		User p5 = new User("tina");
		p5.setName("Tina Syversen");
		
		Meeting model = new Meeting("møte møte møte møte");
		model.setDate(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		model.setLocation(new Place(1, "Kiosken"));
		model.setOwner(user);
		model.addParticipant(p1, State.Pending);
		model.addParticipant(p2, State.Pending);
		model.addParticipant(p3, State.Pending);
		model.addParticipant(p4, State.Accepted);
		model.addParticipant(p5, State.Rejected);
    	
    	
    	
		new MeetingFrame(user, model);
    }
}
