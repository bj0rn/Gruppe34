package no.ntnu.fp.model;

import java.io.Serializable;
import java.util.Date;

public class Appointment extends CalendarEntry implements Serializable{
	
	private static final long serialVersionUID = -1068480439451430890L;

	public Appointment(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	public Appointment(Date start, Date end, String desc, int id) {
		super(desc, start, end, id);
	}


}
