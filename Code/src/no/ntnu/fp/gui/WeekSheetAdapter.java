package no.ntnu.fp.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Meeting;

public class WeekSheetAdapter implements Iterable<CalendarEntryView>{
	List<Calendar> calendars = new ArrayList<Calendar>();
	DateModel dateModel;

	public WeekSheetAdapter(DateModel dateModel) {
		this.dateModel = dateModel;
	}

	public void addCalendar(Calendar calendar) {
		calendars.add(calendar);
		//calendar.addPropertyChangeListener(this);
	}

	public void removeCalendar(Calendar calendar) {
		calendars.remove(calendar);
	}
	
	public List<CalendarEntryView> getEntryViews(){
		//TODO bgcolor entryviews equal to entryviews from the same calendar
		List<CalendarEntryView> entries = new ArrayList<CalendarEntryView>();
		for(Calendar calendar: calendars){
			for(CalendarEntry calendarEntry: calendar){
					if((calendarEntry.getYear()+1900) == dateModel.getYear() && calendarEntry.getWeek() == dateModel.getWeek()){
						CalendarEntryView view = new CalendarEntryView(calendarEntry);
						view.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseClicked(e);
								CalendarEntry ce = ((CalendarEntryView)e.getSource()).getModel();
								if (ce instanceof Meeting) {
									new MeetingFrame((Meeting) ce);
								}
								if (ce instanceof Appointment) {
									new AppointmentPanel((Appointment) ce);
								}
							}
						});
						entries.add(view);
					}
				}
			}
		return entries;
	}

	@Override
	public Iterator<CalendarEntryView> iterator() {
		return new Iterator<CalendarEntryView>() {

			List<CalendarEntryView> entries = getEntryViews();
			int i = entries.size()-1;
			
			@Override
			public boolean hasNext() {
				return i>0;
			}

			@Override
			public CalendarEntryView next() {
				return entries.get(i--);
			}

			@Override
			public void remove() {
				entries.remove(i+1);
			}
			
		};
	}
}
