package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.User;

public class CalendarPanel extends JPanel implements PropertyChangeListener {

	private Navigator weekNavigator;
	private Navigator yearNavigator;
	private WeekSheet weekSheet;
	private WeekSheetAdapter weekSheetAdapter;
	JScrollPane scrollArea;
	private DateModel dateModel;
	private User user;
	private List<User> others;

	public CalendarPanel(User user) {
		// Creates instance with current system time Date object
		this(new DateModel(Calendar.getInstance().getTime()), user);
	}

	public CalendarPanel(DateModel dateModel, User user) {
		setLayout(new BorderLayout(12, 12));

		this.user = user;
		//TEST
		dateModel.setWeek(12);
		//TEST END
		this.dateModel = dateModel;
		dateModel.addPropertyChangeListener(this);

		weekNavigator = new Navigator("Week",dateModel.getWeek(), 1, 52);
		weekNavigator.addPropertyChangeListener(this);

		yearNavigator = new Navigator("Year",dateModel.getYear(), 0, Integer.MAX_VALUE);
		yearNavigator.addPropertyChangeListener(this);

		JPanel top = new JPanel();
		top.add(weekNavigator);
		top.add(yearNavigator);

		weekSheetAdapter = new WeekSheetAdapter(dateModel);
		weekSheetAdapter.addCalendar(user.getCalendar());
		weekSheet = new WeekSheet(weekSheetAdapter);
		scrollArea = new JScrollPane(weekSheet);
		scrollArea.setPreferredSize(new Dimension(600, 300));
		scrollArea.setRowHeaderView(new JLabel(" "));

		add(top, BorderLayout.NORTH);
		add(scrollArea, BorderLayout.CENTER);
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getSource().equals(weekNavigator)) {
			dateModel.setWeek(weekNavigator.getValue());
			System.out.println("Select week: " + weekNavigator.getValue());
		} else if (pce.getSource().equals(yearNavigator)) {
			dateModel.setYear(yearNavigator.getValue());
		}
	}
}
