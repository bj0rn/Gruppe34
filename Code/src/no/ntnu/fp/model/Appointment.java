package no.ntnu.fp.model;

import java.util.Date;

public class Appointment extends CalendarEntry{
	public Appointment(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	public Appointment(String desc, Date start, Date end) {
		super(desc, start, end);
	}


}
