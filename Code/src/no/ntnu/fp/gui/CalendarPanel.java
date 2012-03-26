package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.Appointment;

public class CalendarPanel extends JPanel {

	private Navigator weekNavigator;
	private Navigator yearNavigator;

	JScrollPane scrollArea;

	private WeekSheet weekSheet;
	private DateModel dateModel;
	
	public CalendarPanel() {
		//Creates instance with current system time Date object
		this(new DateModel(Calendar.getInstance().getTime()));
	}
	
	public CalendarPanel(DateModel dateModel) {
		this.dateModel = dateModel;
		
		setLayout(new BorderLayout(12,12));
		
		JPanel top = new JPanel();
		top.add(weekNavigator = new Navigator(dateModel.getWeek(), 1, 52));
		top.add(yearNavigator = new Navigator(dateModel.getYear(), 0, Integer.MAX_VALUE));

		weekSheet = new WeekSheet();
		
		scrollArea = new JScrollPane(weekSheet);
		scrollArea.setPreferredSize(new Dimension(600,300));
		scrollArea.setRowHeaderView(new JLabel(" "));
		JPanel weekheader = new JPanel();

		scrollArea.setColumnHeaderView(weekheader);
		add(top, BorderLayout.NORTH);
		add(scrollArea, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("WeekSheet");
		CalendarPanel cp = new CalendarPanel();
		frame.add(cp);
		frame.setSize(1000,600);
		frame.setVisible(true);
		cp.weekSheet.addCalendarEntryView(new CalendarEntryView(new Appointment(new Date(2012, 01, 01, 14, 00), new Date(2012, 01, 01, 15, 30), "M¿te", 0)));
	}

}
