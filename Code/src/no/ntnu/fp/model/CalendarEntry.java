package no.ntnu.fp.model;

import java.util.Date;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Calendar;

public abstract class CalendarEntry implements Serializable {
	
	private static final long serialVersionUID = -5666618955325756218L;
	
	public final static String MEETING = "Meeting";
	public final static String APPOINTMENT = "Appointment";

	private ModelChangeListener modelChangeListener;
	private String description;
	private User owner;
	private Location location;
	private Date startDate;
	private Date endDate;
	private int id;
	private Calendar cal;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public final static String MODEL_PROPERTY = "Model";
	public final static String DESC_PROPERTY = "Description";
	public final static String OWNER_PROPERTY = "Owner";
	public final static String LOC_PROPERTY = "Location";
	public final static String START_PROPERTY ="Start time";
	public final static String END_PROPERTY ="End time";

	
	public CalendarEntry(int id) {
		this.id = id;
	}
	
	public CalendarEntry(String description) {
		this.description = description;

	}

	public CalendarEntry(String description, Date startDate, Date endDate, int id) {
		this(description);
		this.id = id;
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
	
	public void setStartDate(Date startDate){
		Date oldValue = startDate;
		this.startDate = startDate;
		pcs.firePropertyChange(START_PROPERTY, oldValue, startDate);
		
	}
	public void setEndDate(Date endDate){
		Date oldValue = endDate;
		this.endDate = endDate;
		pcs.firePropertyChange(END_PROPERTY, oldValue, endDate);
	}
	
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Returns the duration of the entry
	 * 
	 * @return the duration of this entry in milliseconds
	 */
	public long getDuration() {
		return endDate.getTime() - startDate.getTime();
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
	
	public int getID() {
		return this.id;
	}

	public void setModelCL(ModelChangeListener modelChangeListener) {
		ModelChangeListener oldValue = this.modelChangeListener;
		this.modelChangeListener = modelChangeListener;
		pcs.firePropertyChange(MODEL_PROPERTY, oldValue, modelChangeListener);
	}

	public ModelChangeListener getModelCL() {
		return modelChangeListener;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(id);
		
		return builder.toString();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
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

	/**
	 * 
	 * @return weekday int corr to {@code Calendar.<DAY>}
	 */
	public int getDayOfWeek() {
		cal = Calendar.getInstance();
		cal.setTime(startDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 
	 * @return minutes since 00:00
	 */
	public int getTimeOfDay() {
		cal = Calendar.getInstance();
		cal.setTime(startDate);
		return cal.get(Calendar.HOUR*60)+cal.get(Calendar.MINUTE);
	}
}
