package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.fp.model.Calendar;

public class CalendarPanel extends JPanel {

	private Navigator weekNavigator;
	private Navigator yearNavigator;

	private JButton newAppointment;
	private JButton newMeeting;
	private JButton calendars;

	private JLabel weekLabel;
	private JLabel yearLabel;

	private MessagePanel messagePanel;

	private WeekSheet weekSheet;

	private List<Calendar> model;

	public CalendarPanel() {
		setLayout(new BorderLayout());

		JPanel top = new JPanel();
		// TODO: Set to current week
		top.add(weekNavigator = new Navigator(1, 1, 52));
		top.add(yearNavigator = new Navigator(2012, 0, Integer.MAX_VALUE));

		JPanel bottom = new JPanel();
		bottom.add(newAppointment = new JButton("Ny avtale"));
		bottom.add(newMeeting = new JButton("Nytt m¿te"));
		bottom.add(newAppointment = new JButton("Kalendere"));

		weekSheet = new WeekSheet();
		add(top, BorderLayout.NORTH);
		add(weekSheet, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		add(messagePanel = new MessagePanel(), BorderLayout.EAST);

	}

	public List<Calendar> getModel() {
		return model;
	}

	public void setModel(List<Calendar> model) {
		this.model = model;
	}

	private void addMockImage() {

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/images/calendar.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		JLabel l = new JLabel(new ImageIcon(img));
		add(l, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("WeekSheet");
		CalendarPanel cp = new CalendarPanel();
		frame.add(cp);
		frame.setVisible(true);
	}

}
