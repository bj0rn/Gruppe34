package no.ntnu.fp.model;

import java.util.Date;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

public abstract class CalendarEntry {

	public final static String MEETING = "Meeting";
	public final static String APPOINTMENT = "Appointment";

	private ModelChangeListener modelChangeListener;
	private String description;
	private User owner;
	private Location location;
	private Date startDate;
	private Date endDate;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public final static String MODEL_PROPERTY = "Model";
	public final static String DESC_PROPERTY = "Description";
	public final static String OWNER_PROPERTY = "Owner";
	public final static String LOC_PROPERTY = "Location";
	private Calendar cal = Calendar.getInstance();

	public CalendarEntry(String description) {
		this.description = description;

	}

	public CalendarEntry(String description, Date startDate, Date endDate) {
		this(description);
		setDate(startDate, endDate);
	}

	/**
	 * Sets entry start and end date
	 * 
	 * @param startDate
	 *            Start {@code Date} of entry
	 * @param endDate
	 *            End {@code Date} of entry
	 */
	public void setDate(Date startDate, Date endDate) {
		if (startDate.compareTo(endDate) > 0) {
			throw new IllegalArgumentException(
					"Start date cannot be after end date!");
		} else {
			this.startDate = startDate;
			this.endDate = endDate;
		}
	}

	/**
	 * 
	 * @return day of week according to {@code static int Calendar.<DAY>}
	 */
	public int getDayOfWeek() {
		cal.setTime(startDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 
	 * @return time of day in minutes since 00:00
	 */
	public int getTimeOfDay() {
		cal.setTime(startDate);
		return cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
	}

	/**
	 * Returns the duration of the entry
	 * 
	 * @return the duration of this entry in minutes
	 */
	public long getDuration() {
		return (endDate.getTime() - startDate.getTime())/60000;
	}

	public void setLocation(Location location) {
		Location oldValue = this.location;
		this.location = location;
		pcs.firePropertyChange(LOC_PROPERTY, oldValue, location);
	}

	public Location getLocation() {
		return location;
	}

	public void setDescription(String description) {
		String oldValue = this.description;
		this.description = description;
		pcs.firePropertyChange(DESC_PROPERTY, oldValue, description);
	}

	public String getDescription() {
		return description;
	}

	public void setOwner(User owner) {
		User oldValue = this.owner;
		this.owner = owner;
		pcs.firePropertyChange(OWNER_PROPERTY, oldValue, owner);
	}

	public User getOwner() {
		return owner;
	}

	public void setModelCL(ModelChangeListener modelChangeListener) {
		ModelChangeListener oldValue = this.modelChangeListener;
		this.modelChangeListener = modelChangeListener;
		pcs.firePropertyChange(MODEL_PROPERTY, oldValue, modelChangeListener);
	}

	public ModelChangeListener getModelCL() {
		return modelChangeListener;
	}

	public enum CalendarEntryType {
		MEETING, APPOINTMENT;

		public String toString() {
			switch (this) {
			case MEETING:
				return "Meeting";
			case APPOINTMENT:
				return "Appointment";
			default:
				return null;
			}
		}
	}

}
