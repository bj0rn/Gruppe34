package no.ntnu.fp.model;

public class CalendarEntry {

	public enum CalendarEntryType {
		MEETING, APPOINTMENT;
		
		public String toString() {
			switch(this) {
				case MEETING: 		return "Meeting";
				case APPOINTMENT: 	return "Appointment";
				default: return null;
			}
		}
	}
	
}
