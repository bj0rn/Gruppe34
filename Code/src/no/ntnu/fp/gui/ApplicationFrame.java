package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.MeetingInviteNotification;
import no.ntnu.fp.model.MeetingReplyNotification;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.storage.db.DbConnection;

public class ApplicationFrame extends JFrame {

	public static void main(String[] args) {

		ApplicationFrame calendar = new ApplicationFrame("havard");
		
	}
	
	private final CommunicationController c = CommunicationController.getInstance();
	
	private User model;
	
	private CalendarPanel calendarPanel;
	private NotificationPanel notificationPanel;
	
	private JButton newAppointmentButton;
	private JButton newMeetingButton;
	private JButton calendarButton;
	
	public ApplicationFrame(String user) {
		
		//testSetUser();
		setModel(c.getClientFullUser());
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		// Add Calendar Panel
		calendarPanel = new CalendarPanel(getModel());
		panel.add(calendarPanel, BorderLayout.CENTER);
		
		// Add Notification Panel
		notificationPanel = new NotificationPanel(model);
		panel.add(notificationPanel, BorderLayout.EAST);
		
		// Add Buttons
		JPanel buttons = new JPanel();
		
		newAppointmentButton = new JButton("Ny Avtale");
		newAppointmentButton.addActionListener(new NewAppointmentAction(this));
		buttons.add(newAppointmentButton);

		newMeetingButton = new JButton("Nytt Møte");
		newMeetingButton.addActionListener(new NewMeetingAction(this, getModel()));
		buttons.add(newMeetingButton);
		
		calendarButton = new JButton("Kalendere");
		calendarButton.addActionListener(new OpenCalendarAction(this));
		buttons.add(calendarButton);
		
		panel.add(buttons, BorderLayout.SOUTH);
		
		
		setPreferredSize(new Dimension(900, 600));
		setContentPane(panel);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//testUpdate();
	}
	
	private void testUpdate() {
		User user = new User("havard");
		user.setName("Håvard Wormdal Høiby");
		
		User p1 = new User("bjorn");
		p1.setName("Bjørn Åge Tungesvik");
		
		Meeting m1 = new Meeting("UppdateTestMeeting");
		m1.setID(20);
		m1.setDate(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		m1.setOwner(p1);
		m1.addParticipant(user, State.Pending);
		m1.setLocation(new Place(1,"Plass"));
		
		c.updateMeeting(m1);
		
	}

	private void testSetUser() {
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
		
		setModel(p2);
	}
	
	private void setModel(User model) {
		this.model = model;
	}
	
	public User getModel() {
		return model;
	}
	
}
