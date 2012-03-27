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
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(description);
		System.out.println(id);
		Appointment andeplan = new Appointment(startDate, endDate, description, id);
		System.out.println(andeplan);
		andeplan.setOwner(new User(getOwner().getUsername()));
		return andeplan;
	}


}
