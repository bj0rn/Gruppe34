package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.User;

public class ApplicationPanel extends JPanel {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		ApplicationPanel calendar = new ApplicationPanel();
		frame.setContentPane(calendar);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private User model;
	
	private CalendarPanel calendarPanel;
	private NotificationPanel notificationPanel;
	
	private JButton newAppointmentButton;
	private JButton newMeetingButton;
	private JButton calendarButton;
	
	public ApplicationPanel() {
		
		setLayout(new BorderLayout());
		
		// Add Calendar Panel
		calendarPanel = new CalendarPanel();
		add(calendarPanel, BorderLayout.CENTER);
		
		// Add Notification Panel
		notificationPanel = new NotificationPanel();
		add(notificationPanel, BorderLayout.EAST);
		
		// Add Buttons
		JPanel buttons = new JPanel();
		
		newAppointmentButton = new JButton("Ny Avtale");
		newAppointmentButton.addActionListener(new NewAppointmentAction(this));
		buttons.add(newAppointmentButton);

		newMeetingButton = new JButton("Nytt Møte");
		newMeetingButton.addActionListener(new NewMeetingAction(this));
		buttons.add(newMeetingButton);
		
		calendarButton = new JButton("Kalendere");
		calendarButton.addActionListener(new OpenCalendarAction(this));
		buttons.add(calendarButton);
		
		add(buttons, BorderLayout.SOUTH);
	}
	
	public void setModel(User model) {
		this.model = model;
	}
	
	public User getModel() {
		return model;
	}
	
}
