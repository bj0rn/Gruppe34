package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ntnu.fp.model.Appointment;

public class CalendarPanel extends JPanel {

	private Navigator weekNavigator;
	private Navigator yearNavigator;

	private JButton newAppointment;
	private JButton newMeeting;
	private JButton calendars;

	private JLabel weekLabel;
	private JLabel yearLabel;
	JScrollPane scrollArea;

	private WeekSheet weekSheet;

	public CalendarPanel() {
		setLayout(new BorderLayout(12,12));

		JPanel top = new JPanel();
		// TODO: Set to current week
		top.add(weekNavigator = new Navigator(1, 1, 52));
		top.add(yearNavigator = new Navigator(2012, 0, Integer.MAX_VALUE));

		weekSheet = new WeekSheet();

		
		scrollArea = new JScrollPane(weekSheet);
		scrollArea.setPreferredSize(new Dimension(600,300));
		scrollArea.setRowHeaderView(new JLabel(" "));
		JPanel weekheader = new JPanel();
		weekheader.add(new JLabel("Kl."));
		String[] weekDays = {"Mandag", "Tirsdag", "Onsdag","Torsdag","Fredag","L�rdag","S�ndag"};
		JLabel d;
		for(String s:weekDays){
			d= new JLabel(s);
			d.setSize(300, 100);
			weekheader.add(d);
		}
		scrollArea.setColumnHeaderView(weekheader);
		add(top, BorderLayout.NORTH);
		add(scrollArea, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("WeekSheet");
		CalendarPanel cp = new CalendarPanel();
		frame.setContentPane(cp);
		frame.setSize(1000,600);
		frame.setVisible(true);
		cp.weekSheet.addCalendarEntryView(new CalendarEntryView(new Appointment(new Date(2012, 01, 01, 14, 00), new Date(2012, 01, 01, 15, 30), "M�te", 0)));
	}

}
