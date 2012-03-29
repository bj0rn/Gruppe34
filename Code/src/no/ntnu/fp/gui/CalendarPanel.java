package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.User;
import no.ntnu.fp.util.Log;

public class CalendarPanel extends JPanel implements PropertyChangeListener {

	public static final String DAYS[] = new String[] { "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag" };
	
	private Navigator weekNavigator;
	private Navigator yearNavigator;
	private WeekSheet weekSheet;
	private WeekSheetAdapter weekSheetAdapter;
	private JScrollPane scrollArea;
	private DateModel dateModel;
	private User user;
	private List<User> others;
	private JPanel header;

	public CalendarPanel(User user) {
		// Creates instance with current system time Date object
		this(new DateModel(Calendar.getInstance().getTime()), user);
	}

	public CalendarPanel(DateModel date, User user) {
		setLayout(new BorderLayout(12, 12));
		
		this.user = user;
		this.dateModel = date;

		weekNavigator = new WeekNavigator(dateModel); // = new Navigator("Week",date.getWeek(), 1, 52);
		weekNavigator.addPropertyChangeListener(this);

		yearNavigator = new YearNavigator(dateModel); //= new Navigator("Year",date.getYear(), 0, Integer.MAX_VALUE);
		yearNavigator.addPropertyChangeListener(this);

		JPanel top = new JPanel();
		top.add(weekNavigator);
		top.add(yearNavigator);

		JPanel weekSheetPanel = new JPanel();
		weekSheetPanel.setLayout(new BorderLayout());
		
		weekSheetAdapter = new WeekSheetAdapter(this.dateModel, user.getCalendar());
		weekSheet = new WeekSheet(weekSheetAdapter);
		this.dateModel.addPropertyChangeListener(weekSheet);
		scrollArea = new JScrollPane(weekSheet);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollArea.setPreferredSize(new Dimension(600, 300));
		scrollArea.setRowHeaderView(new JLabel(" "));
		
		header = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				
				DateModel date = new DateModel(dateModel.getDate());
				Calendar c = Calendar.getInstance();
				
				c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
				
				date.setDay(c.get(Calendar.DATE)+1);
				
				for (int i=0; i<7; i++) {
					String label = DAYS[i] + " (" + date.getDay() + "." + (date.getMonth()+1) + ")";
					g.drawString(label, 50 + (100*i), 9);
					date.setDay(date.getDay()+1);
				}
			}
		};
		header.setSize(new Dimension(600, 20));
		
		weekSheetPanel.add(header, BorderLayout.NORTH);
		weekSheetPanel.add(scrollArea, BorderLayout.CENTER);
		
		add(top, BorderLayout.NORTH);
		add(weekSheetPanel, BorderLayout.CENTER);
	}

	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(Navigator.VALUE_PROPERTY)) {
			if (pce.getSource().equals(weekNavigator)) {
				dateModel.setWeek((Integer) pce.getNewValue());
			} else if (pce.getSource().equals(yearNavigator)) {
				dateModel.setYear((Integer) pce.getNewValue());
			}
			scrollArea.getVerticalScrollBar().setValue(400);
			
			header.repaint();
		}
	}
	
	@Override
	public String toString() {
		return "CalendarPanel";
	}
}
