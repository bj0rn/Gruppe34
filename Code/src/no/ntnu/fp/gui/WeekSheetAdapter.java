package no.ntnu.fp.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.util.TimeLord;

public class WeekSheetAdapter implements Iterable<CalendarEntryView>{
	List<Calendar> calendars = new ArrayList<Calendar>();
	DateModel dateModel;

	public WeekSheetAdapter(DateModel dateModel) {
		this.dateModel = dateModel;
	}

	public void addCalendar(Calendar calendar) {
		calendars.add(calendar);
	}

	public void removeCalendar(Calendar calendar) {
		calendars.remove(calendar);
	}
	
	public List<CalendarEntryView> getEntryViews(){
		//TODO bgcolor entryviews equal to entryviews from the same calendar
		List<CalendarEntryView> entries = new ArrayList<CalendarEntryView>();
		for(Calendar calendar: calendars){
			for(CalendarEntry calendarEntry: calendar){
				System.out.println(calendarEntry.getWeek() + " " + calendarEntry.getYear());
					if((calendarEntry.getYear()+1900) == dateModel.getYear() && calendarEntry.getWeek() == dateModel.getWeek()){
						entries.add(new CalendarEntryView(calendarEntry));
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
