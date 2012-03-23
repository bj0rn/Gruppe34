package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.CellEditor;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.MeetingInviteNotification;
import no.ntnu.fp.model.MeetingNotification;
import no.ntnu.fp.model.MeetingReplyNotification;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.util.TimeLord;

public class NotificationPanel extends JPanel {

	public static final String MEETING_INVITATION_TITLE = "Invitasjon";
	public static final String MEETING_LOCATION_LABEL = "Hvor: ";
	public static final String MEETING_FROM_TIME_LABEL = "Fra: ";
	public static final String MEETING_TO_TIME_LABEL = "Til: ";
	public static final String ACCECT_BUTTON_LABEL = "Ja";
	public static final String REJECT_BUTTON_LABEL = "Nei";
	public static final String MEETING_REPLY_TITLE = "Svar";
	public static final String MEETING_REPLY_EDIT_BUTTON_LABEL = "Endre Møte";
	public static final String SHOW_BUTTON_LABEL = "Vis";

	private User user;
	
	private JLabel titleLabel;
	
	private JEditableList notificationList;
	private NotificationListModel notificationListModel;
	
	public NotificationPanel(User user) {
		
		setUser(user);
		
		setLayout(new BorderLayout());
		
		titleLabel = new JLabel("Meldinger");	
		titleLabel.setFont(StylingDefinition.FRAME_TITLE_FONT);
		titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(titleLabel, BorderLayout.NORTH);
		
		notificationListModel = new NotificationListModel();
		setModel(user.getNotifications());
		
		notificationList = new JEditableList(notificationListModel, new NotificationCellRenderer());
		notificationList.setMinimumSize(new Dimension(300, 800));
		
		JScrollPane scrollPane = new JScrollPane(notificationList);
		
		add(scrollPane, BorderLayout.CENTER);
		
		setMinimumSize(new Dimension(300, 500));
		
		
	}
	
	private void setUser(User user) {
		this.user = user;
	}

	public void setModel(List<Notification> model) {
		notificationListModel.setNotifications(model);
	}
	
	public class NotificationCellRenderer implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Notification notif = (Notification)notificationListModel.getElementAt(index);
			
			JPanel panel = new JPanel();
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
			Border margin = new EmptyBorder(0,1,1,1);
			
			panel.setBorder(new CompoundBorder(margin, border));
			
			if (notif instanceof MeetingNotification) {
			
				
				Meeting meeting = ((MeetingNotification) notif).getMeeting();
				if (notif instanceof MeetingReplyNotification) {
					
					User user = ((MeetingNotification) notif).getUser();
					State state = meeting.getState(user);

					panel.setMinimumSize(new Dimension(300,22));
					panel.setLayout(new BorderLayout());
					
					JLabel title = new JLabel(MEETING_REPLY_TITLE);
					title.setFont(StylingDefinition.NOTIFICATION_TITLE);
					title.setBorder(new EmptyBorder(4, 4, 4, 4));
					panel.add(title, BorderLayout.NORTH);
					
					
					JTextArea textComp = new JTextArea();
					textComp.setEditable(false);
					textComp.setLineWrap(true);
					textComp.setMargin(new Insets(4,4,4,4));
					
					String text = user.getName() + " har " + state + " din møte innkalling";
					textComp.setText(text);
					
					
					panel.add(textComp, BorderLayout.CENTER);
					
					JButton editMeetingButton = new JButton(MEETING_REPLY_EDIT_BUTTON_LABEL);
					editMeetingButton.addActionListener(new MeetingReplyEditButtonAction(meeting, user));
					panel.add(editMeetingButton, BorderLayout.SOUTH);
					
				} else { 
					assert(notif instanceof MeetingInviteNotification);

					panel.setMinimumSize(new Dimension(300,200));
					panel.setLayout(new BorderLayout());
					
					JLabel title = new JLabel(MEETING_INVITATION_TITLE);
					title.setFont(StylingDefinition.NOTIFICATION_TITLE);
					title.setBorder(new EmptyBorder(4, 4, 4, 4));
					panel.add(title, BorderLayout.NORTH);
					
					JPanel content = new JPanel();
					content.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
					
					
					JLabel descComp = new JLabel(meeting.getDescription());
					addGridBagComponent(content, descComp, 0, 0, c, 2);
					
					JLabel locationLabel = new JLabel(MEETING_LOCATION_LABEL);
					addGridBagLabel(content, locationLabel, 1, c);
					JLabel locationComp = new JLabel(meeting.getLocation().toString());
					addGridBagComponent(content, locationComp, 1, c);
					
					JLabel startLabel = new JLabel(MEETING_FROM_TIME_LABEL);
					addGridBagLabel(content, startLabel, 2, c);
					JLabel startComp = new JLabel(TimeLord.formatDate(meeting.getStartDate()));
					addGridBagComponent(content, startComp, 2, c);
					
					JLabel endLabel = new JLabel(MEETING_TO_TIME_LABEL);
					addGridBagLabel(content, endLabel, 3, c);
					JLabel endComp = new JLabel(TimeLord.formatDate(meeting.getEndDate()));
					addGridBagComponent(content, endComp, 3, c);
					
					panel.add(content, BorderLayout.CENTER);

					JPanel buttonPanel = new JPanel();
					
					JButton acceptButton = new JButton(ACCECT_BUTTON_LABEL);
					acceptButton.addActionListener(new MeetingInviteAcceptButtonAction(meeting, user));
					buttonPanel.add(acceptButton);
					
					JButton rejectButton = new JButton(REJECT_BUTTON_LABEL);
					rejectButton.addActionListener(new MeetingInviteRejectButtonAction(meeting, user));
					buttonPanel.add(rejectButton);
					
					JButton showButton = new JButton(SHOW_BUTTON_LABEL);
					showButton.addActionListener(new MeetingInviteShowButtonAction(meeting, user));
					buttonPanel.add(showButton);
					
					panel.add(buttonPanel, BorderLayout.SOUTH);
					
				
				}
			}
			
			return panel;
		}
	}
	
	private void addGridBagLabel(JPanel panel, JLabel label, int row, GridBagConstraints c) {
		c.gridx = 0;
		c.gridy = row;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_START;
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();

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
		
		Meeting m1 = new Meeting("Møte 1");
		m1.setDate(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		m1.setLocation(new Place(1, "Kiosken"));
		m1.setOwner(user);
		m1.addParticipant(p1, State.Pending);
		m1.addParticipant(p2, State.Pending);
		m1.addParticipant(p3, State.Pending);
		m1.addParticipant(p4, State.Accepted);
		m1.addParticipant(p5, State.Rejected);
		
		Meeting m2 = new Meeting("Møte 2");
		m2.setDate(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		m2.setLocation(new Room(1, "414", "P15", 30));
		m2.setOwner(p2);
		m2.addParticipant(user, State.Rejected);
		
		MeetingInviteNotification n1 = new MeetingInviteNotification();
		n1.setUser(p2);
		n1.setMeeting(m1);
		
		MeetingReplyNotification n2 = new MeetingReplyNotification();
		n2.setUser(user);
		n2.setMeeting(m2);
		
		
		
		List<Notification> model = new ArrayList<Notification>();
		model.add(n1);
		model.add(n2);
		
		NotificationPanel panel = new NotificationPanel(p2);
		panel.setModel(model);
		
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
}
