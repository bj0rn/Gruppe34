package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.util.Log;

public class WeekSheetAdapter implements Iterable<CalendarEntryView>, PropertyChangeListener {
	
	private Calendar calendar;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	List<Calendar> calendars = new ArrayList<Calendar>();
	DateModel dateModel;

	public WeekSheetAdapter(DateModel dateModel, Calendar calendar) {
		this.dateModel = dateModel;
		this.calendar = calendar;
		calendar.addPropertyChangeListener(this);
		
		CommunicationController c = CommunicationController.getInstance();
		for (User user : c.getSelectedUsers()) {
			addCalendar(user.getCalendar());
		}
		
	}

	public void addCalendar(Calendar calendar) {
		calendars.add(calendar);
		calendar.addPropertyChangeListener(this);
	}

	public void removeCalendar(Calendar calendar) {
		calendars.remove(calendar);
		calendar.removePropertyChangeListener(this);
	}
	
	public List<CalendarEntryView> getEntryViews(){
		//TODO bgcolor entryviews equal to entryviews from the same calendar
		List<CalendarEntryView> entries = new ArrayList<CalendarEntryView>();
		
		for(CalendarEntry calendarEntry: calendar){
			
			Log.out(dateModel.getWeek());
			Log.out(calendarEntry.getDayOfWeek());
			
			boolean inYear = (calendarEntry.getYear()+1900) == dateModel.getYear();
			boolean isSunday = calendarEntry.getDayOfWeek() == 5;
			boolean inWeek = isSunday ? calendarEntry.getWeek() == dateModel.getWeek()+1 : calendarEntry.getWeek() == dateModel.getWeek();
			Log.out(dateModel.getWeek(), dateModel.getYear());
			Log.out(calendarEntry.getDescription(),calendarEntry.getWeek(),calendarEntry.getYear(), inYear, inWeek);
			
			
			if(inYear && inWeek){
				if (calendarEntry instanceof Meeting) {
					Meeting m = (Meeting) calendarEntry;
					
					User user = CommunicationController.getInstance().getUser();
					
					if (m.getOwner().equals(user) || m.getState(user) == State.Accepted) {
					
						CalendarEntryView view = new CalendarEntryView(calendarEntry);
						view.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseClicked(e);
								CalendarEntry ce = ((CalendarEntryView)e.getSource()).getModel();
								
								Meeting m = (Meeting) ce;
								
								if (m.getOwner().equals(CommunicationController.getInstance().getUser())) {
									new MeetingFrame(m);
								} else {
									new MeetingInviteFrame(m);
								}
							}
						});
						entries.add(view);
					}
				}
				if (calendarEntry instanceof Appointment) {
					CalendarEntryView view = new CalendarEntryView(calendarEntry);
					view.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							super.mouseClicked(e);
							CalendarEntry ce = ((CalendarEntryView)e.getSource()).getModel();
							new AppointmentPanel((Appointment) ce);
						}
					});
					entries.add(view);
				}
			}
		}
		
		
		for (Calendar calendar : calendars) {
			
			for(CalendarEntry calendarEntry: calendar){
				if((calendarEntry.getYear()+1900) == dateModel.getYear() && calendarEntry.getWeek() == dateModel.getWeek()){
					if (calendarEntry instanceof Meeting) {
						Meeting m = (Meeting) calendarEntry;
						
						User user = CommunicationController.getInstance().getUser();
						
						if (m.getOwner().equals(user) || m.getState(user) == State.Accepted) {
						
							CalendarEntryView view = new CalendarEntryView(calendarEntry);
							view.setBackground(Color.BLUE);
							/*view.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseClicked(MouseEvent e) {
									// TODO Auto-generated method stub
									super.mouseClicked(e);
									CalendarEntry ce = ((CalendarEntryView)e.getSource()).getModel();
									
									Meeting m = (Meeting) ce;
									
									if (m.getOwner().equals(CommunicationController.getInstance().getUser())) {
										new MeetingFrame(m);
									} else {
										new MeetingInviteFrame(m);
									}
								}
							});*/
							entries.add(view);
						}
					}
					if (calendarEntry instanceof Appointment) {
						CalendarEntryView view = new CalendarEntryView(calendarEntry);
						view.setBackground(Color.BLUE);
						/*view.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseClicked(e);
								CalendarEntry ce = ((CalendarEntryView)e.getSource()).getModel();
								new AppointmentPanel((Appointment) ce);
							}
						});*/
						entries.add(view);
					}
				}
			}
		}
		
		
		return entries;
	}

	@Override
	public Iterator<CalendarEntryView> iterator() {
		return new Iterator<CalendarEntryView>() {

			List<CalendarEntryView> entries = getEntryViews();
			int i = 0;
			
			@Override
			public boolean hasNext() {
				return i<entries.size();
			}

			@Override
			public CalendarEntryView next() {
				return entries.get(i++);
			}

			@Override
			public void remove() {
				entries.remove(i);
			}
			
		};
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
	}
}
