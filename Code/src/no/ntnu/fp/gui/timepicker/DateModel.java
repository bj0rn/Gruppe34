package no.ntnu.fp.gui.timepicker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;

import no.ntnu.fp.util.TimeLord;


public class DateModel {

	private PropertyChangeSupport pcs;
	
	private Date date;
	
	public final static String YEAR_PROPERTY = "Year";
	public final static String MONTH_PROPERTY = "Month";
	public final static String DAY_PROPERTY = "Day";
	public final static String WEEK_PROPERTY = "Week";
	public final static String HOURS_PROPERTY = "Hour";
	public final static String MINUTES_PROPERTY = "Minute";
	public final static String SECONDS_PROPERTY = "Seconds";
	
	
	public DateModel() {
		this(new Date());
	}
	
	public DateModel(Date date) {
		this.date = date;
		pcs = new PropertyChangeSupport(this);
	}
	
	public void setDate(Date date) {
		setYear(date.getYear());
		setMonth(date.getMonth());
		setDay(date.getDay());
		setHours(date.getHours());
		setMinutes(date.getMinutes());
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setYear(int year) {
		int oldValue = getYear();
		date.setYear(year);
		pcs.firePropertyChange(YEAR_PROPERTY, oldValue, year);
	}
	
	public int getYear() {
		return date.getYear() + 1900;
	}
	
	public void setMonth(int month) {
		int oldValue = getMonth();
		date.setMonth(month);
		pcs.firePropertyChange(MONTH_PROPERTY, oldValue, month);
	}
	
	public int getMonth() {
		return date.getMonth();
	}
	
	public void setDay(int day) {
		int oldValue = getDay();
		date.setDate(day);
		pcs.firePropertyChange(DAY_PROPERTY, oldValue, day);
	}
	
	public int getDay() {
		return date.getDate();
	}
	
	public void setHours(int hour) {
		int oldValue = getHours();
		date.setHours(hour);
		pcs.firePropertyChange(HOURS_PROPERTY, oldValue, hour);
	}
	
	public int getHours() {
		return date.getHours();
	}
	
	public void setMinutes(int minutes) {
		int oldValue = getMinutes();
		date.setMinutes(minutes);
		pcs.firePropertyChange(MINUTES_PROPERTY, oldValue, minutes);
	}
	
	public int getMinutes() {
		return date.getMinutes();
	}
	
	public void setSeconds(int seconds) {
		int oldValue = getSeconds();
		date.setSeconds(seconds);
		pcs.firePropertyChange(SECONDS_PROPERTY, oldValue, seconds);
	}
	
	public int getSeconds() {
		return date.getSeconds();
	}
	
	public int getMonthStartDay() {
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, getMonth());
		c.set(Calendar.DATE, 1);
		
		return (c.get(Calendar.DAY_OF_WEEK)+5)%7;
	}
	
	public int getDaysInMonth() {
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, getMonth());
		
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public String toString() {
		return TimeLord.formatDate(date);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}

	public int getWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}	
	
	public void setWeek(int week){
		int old = getWeek();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate());
		cal.set(Calendar.WEEK_OF_YEAR, week);
		date = cal.getTime();
		pcs.firePropertyChange(WEEK_PROPERTY, old ,week);
	}
}
