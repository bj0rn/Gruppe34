package no.ntnu.fp.model;

import java.util.Date;

public class Appointment extends CalendarEntry{
	public Appointment(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	public Appointment(Date start, Date end, String desc, int id) {
		super(desc, start, end, id);
	}
	
	public Appointment shallowCopy() {
		Appointment andeplan = new Appointment(startDate, endDate, description, id);
		andeplan.setOwner(new User(getOwner().getUsername()));
		andeplan.setLocation(getLocation());
		return andeplan;
	}


}
