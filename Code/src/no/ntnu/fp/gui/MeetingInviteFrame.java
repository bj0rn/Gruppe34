package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.omg.CosNaming.IstringHelper;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;
import no.ntnu.fp.util.StringHelper;
import no.ntnu.fp.util.TimeLord;

public class MeetingInviteFrame extends JFrame implements PropertyChangeListener, ActionListener {

	private Meeting model;
	private User user;
	
	public static final String TITLE_LABEL = "Møteinvitasjon";
	public static final String DESCRIPTION_LABEL = "Beskrivelse";
	public static final String START_LABEL = "Start";
	public static final String END_LABEL = "Slutt";
	public static final String LOCATION_LABEL = "Sted";
	public static final String LEADER_LABEL = "Møteleder";
	public static final String PARTICIPANTS_LABEL = "Deltagere";
	public static final String STATE_LABEL = "Status";
	
	public static final String ACCEPT_RADIO_LABEL = "Aksepter";
	public static final String REJECT_RADIO_LABEL = "Avvis";
	public static final String PENDING_RADIO_LABEL = "Venter";
	
	public static final String MEETING_ACCEPTED = "Akseptert";
	public static final String MEETING_REJECTED = "Avvist";
	public static final String MEETING_PENDING = "Venter på svar";
	
	public static final String OK_BUTTON_LABEL = "OK";
	public static final String CANCEL_BUTTON_LABEL = "Avbryt";
	
	private JTextArea descriptionLabel;
	private JLabel startLabel;
	private JLabel endLabel;
	private JLabel locationLabel;
	private JLabel leaderLabel;
	private JPanel participantsPanel;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private ButtonGroup stateButtonGroup;
	private JRadioButton acceptButton;
	private JRadioButton pendingButton;
	private JRadioButton rejectButton;

	
	public MeetingInviteFrame(Meeting model, User user) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(TITLE_LABEL);
		title.setFont(StylingDefinition.FRAME_TITLE_FONT);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		title.setBorder(padding);
		panel.add(title, BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		
		center.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		addGridBagLabel(center, DESCRIPTION_LABEL, 0, c);
		descriptionLabel = new JTextArea();
		descriptionLabel.setWrapStyleWord(true);
		descriptionLabel.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		addGridBagComponent(center, descriptionLabel, 0, c);
		
		addGridBagLabel(center, START_LABEL, 1, c);
		startLabel = new JLabel();
		startLabel.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		addGridBagComponent(center, startLabel, 1, c);
		
		addGridBagLabel(center, END_LABEL, 2, c);
		endLabel = new JLabel();
		endLabel.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		addGridBagComponent(center, endLabel, 2, c);
		
		addGridBagLabel(center, LOCATION_LABEL, 3, c);
		locationLabel = new JLabel();
		locationLabel.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		addGridBagComponent(center, locationLabel, 3, c);
		
		addGridBagLabel(center, LEADER_LABEL, 4, c);
		leaderLabel = new JLabel();
		leaderLabel.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		addGridBagComponent(center, leaderLabel, 4, c);
		
		JLabel participantsLabel = new JLabel(PARTICIPANTS_LABEL);
		participantsLabel.setFont(StylingDefinition.DIALOG_LABEL_FONT);
		addGridBagComponent(center, participantsLabel, 5, 0, c, 2);
		participantsPanel = new JPanel();
		participantsPanel.setLayout(new BoxLayout(participantsPanel, BoxLayout.Y_AXIS));
		participantsPanel.setMinimumSize(new Dimension(400, 20));
		addGridBagComponent(center, participantsPanel, 6, 0, c, 2);		
				
		JPanel buttonGroupPanel = new JPanel();
		buttonGroupPanel.setLayout(new BoxLayout(buttonGroupPanel, BoxLayout.Y_AXIS));
		stateButtonGroup = new ButtonGroup();
		
		acceptButton = new JRadioButton(ACCEPT_RADIO_LABEL);
		acceptButton.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		stateButtonGroup.add(acceptButton);
		buttonGroupPanel.add(acceptButton);
		acceptButton.addActionListener(this);
		
		rejectButton = new JRadioButton(REJECT_RADIO_LABEL);
		rejectButton.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		stateButtonGroup.add(rejectButton);
		buttonGroupPanel.add(rejectButton);
		rejectButton.addActionListener(this);
		
		pendingButton = new JRadioButton(PENDING_RADIO_LABEL);
		pendingButton.setFont(StylingDefinition.DIALOG_VALUE_FONT);
		stateButtonGroup.add(pendingButton);
		buttonGroupPanel.add(pendingButton);
		pendingButton.addActionListener(this);

		addGridBagLabel(center, STATE_LABEL, 7, c);
		addGridBagComponent(center, buttonGroupPanel, 7, c);

		panel.add(center, BorderLayout.CENTER);

		JPanel buttons = new JPanel();

		setUser(user);
		setModel(model);
		
		okButton = new JButton(OK_BUTTON_LABEL);
		okButton.addActionListener(new OkMeetingInviteAction(this));
		buttons.add(okButton);
		
		cancelButton = new JButton(CANCEL_BUTTON_LABEL);
		cancelButton.addActionListener(new CancelMeetingInviteAction(this));
		buttons.add(cancelButton);		
		
		panel.add(buttons, BorderLayout.SOUTH);
		
		
		okButton.requestFocus();
		
		setMinimumSize(new Dimension(300, 400));
		setContentPane(panel);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setModel(Meeting model) {
		if (model != null) {
			
			if (this.model != null) {
				model.removePropertyChangeListener(this);
			}
			
			this.model = model;
			model.addPropertyChangeListener(this);
			updatePanel();
			
		}
	}

	public Meeting getModel() {
		return model;
	}
	
	private void setUser(User user) {
		this.user = user;
		updatePanel();
	}
	
	public User getUser() {
		return user;
	}
	
	private void addGridBagLabel(JPanel panel, String s, int row, GridBagConstraints c) {
		c.gridx = 0;
		c.gridy = row;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		JLabel label = new JLabel(s);
		label.setFont(StylingDefinition.DIALOG_LABEL_FONT);
		panel.add(label, c);
	}
	
	private void addGridBagComponent(JPanel panel, Component comp, int row, GridBagConstraints c) {
		addGridBagComponent(panel, comp, row, 1, c, 1);
	}
	
	private void addGridBagComponent(JPanel panel, Component comp, int row, int col, GridBagConstraints c, int width) {
		c.gridx = col;
		c.gridy = row;
		c.gridheight = 1;
		c.gridwidth = width;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
		panel.add(comp, c);	
	}
		
	private void updatePanel() {
		if (model != null) {
			updateDescriptionLabel();
			updateStartLabel();
			updateEndLabel();
			locationLabel.setText(model.getLocation().toString());
			leaderLabel.setText(model.getOwner().getName());
			updateParticipantsPanel();
			updateStateButtonGroup();
		}
	}

	private void updateDescriptionLabel() {
		descriptionLabel.setText(model.getDescription());
	}

	private void updateParticipantsPanel() {
		int i=0;
		int n=participantsPanel.getComponents().length;
		for (User user : model.getParticipants()) {
			State state = model.getState(user);
			
			String text = user.getName();
			
			switch(state) {
				case Accepted: text += " - " + MEETING_ACCEPTED; break;
				case Rejected: text += " - " + MEETING_REJECTED; break;
				case Pending:  text += " - " + MEETING_PENDING;  break;
			}
			if (i < n) {
				((JLabel)participantsPanel.getComponent(i)).setText(text);
				i++;
			} else {
				JLabel label = new JLabel(text);
				label.setFont(StylingDefinition.DIALOG_VALUE_FONT);
				participantsPanel.add(label);
			}
		}
	}

	private void updateStateButtonGroup() {
		State state = model.getState(user);
		switch(state) {
			case Accepted:  acceptButton.setSelected(true); break;
			case Pending: 	pendingButton.setSelected(true); break;
			case Rejected:  rejectButton.setSelected(true); break;
		}
	}

	private void updateStartLabel() {
		startLabel.setText(TimeLord.formatDate(model.getStartDate()));
	}

	private void updateEndLabel() {
		endLabel.setText(TimeLord.formatDate(model.getEndDate()));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String property = evt.getPropertyName();

		if (property == Meeting.DESC_PROPERTY) {
			updateDescriptionLabel();
		} else if (property == Meeting.START_PROPERTY) {
			updateStartLabel();
		} else if (property == Meeting.END_PROPERTY) {
			updateEndLabel();
		} else if (property == Meeting.LOC_PROPERTY) {
			locationLabel.setText(model.getLocation().toString());
		} else if (property == Meeting.OWNER_PROPERTY) {
			leaderLabel.setText(model.getOwner().getName());
		} else if (property == Meeting.STATE_PROPERTY) {
			updateStateButtonGroup();
		} else if (property == Meeting.PARTICIPANTS_PROPERTY) {
			updateParticipantsPanel();
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
		
		Meeting model = new Meeting("møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte møte ");
		model.setDate(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		model.setLocation(new Place(1, "Kiosken"));
		model.setOwner(user);
		model.addParticipant(p1, State.Pending);
		model.addParticipant(p2, State.Pending);
		model.addParticipant(p3, State.Pending);
		model.addParticipant(p4, State.Accepted);
		model.addParticipant(p5, State.Rejected);
		
		MeetingInviteFrame m = new MeetingInviteFrame(model, p2);
		
		model.setStartDate(new Date(112, 3, 1, 12, 0, 0));
		model.setEndDate(new Date(112, 3, 1, 13, 0, 0));
		model.setLocation(new Room(1, "name", "desc", 1));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == acceptButton) {
			model.setState(user, State.Accepted);
		} else if (source == rejectButton) {
			model.setState(user, State.Rejected);
		} else if (source == pendingButton) {
			model.setState(user, State.Pending);
		}
	}
}
