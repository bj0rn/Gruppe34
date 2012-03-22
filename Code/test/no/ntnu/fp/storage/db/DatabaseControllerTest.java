package no.ntnu.fp.storage.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.User;

import junit.framework.TestCase;

public class DatabaseControllerTest extends TestCase {

	private DatabaseController ctrl;
	
	public void testAutenticate() {
		
		ctrl = new DatabaseController();
		
		String username = "havard";
		String password = "test";
		String falsePassword = "test1";
		
		try {
			assertTrue(ctrl.authenticate(username, password));
			assertFalse(ctrl.authenticate(username, falsePassword));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetFullUser() {
		
		String username = "havard";
		
		ctrl = new DatabaseController();
		
		try {
			User user = ctrl.getFullUser(username);
			
			assertEquals("havard", user.getUsername());
			assertEquals("Håvard Wormdal Høiby", user.getName());
			assertEquals(24, user.getAge());
			assertEquals(95933245, user.getPhoneNumber());
			assertEquals("havardwhoiby@gmail.com", user.getEmail());
			
			Calendar calendar = user.getCalendar();
			
			assertEquals(1, calendar.getNumEntries());
			
			Meeting meeting = (Meeting) calendar.get(0);
			
			//assertEquals(new Date(2012, 2, 21, 12, 0, 0), meeting.getStartDate());
			//assertEquals(new Date(2012, 2, 21, 13, 0, 0), meeting.getEndDate());
			//assertEquals("TestMøte", meeting.getDescription());
			
			assertEquals(1, meeting.getNumParticipants());

			Set<User> participants = meeting.getParticipants();
			User participant = participants.toArray(new User[] {})[0];
			
			assertEquals("bjorn", participant.getUsername());
			assertEquals("bjorninator", participant.getName());
			assertEquals(23, participant.getAge());
			assertEquals(6565656, participant.getPhoneNumber());
			assertEquals("test@test.com", participant.getEmail());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//testGetFullUser()
	
	public void testSaveAppointment() throws SQLException {
		
		String description = "butts!";
		Place pl = new Place(39, "it's where the butts go!" );
		Date start = new Date();
		Date end = start;
		end.setHours(start.getHours() + 2);
		Appointment app = new Appointment(start, end, description, -1);
		app.setLocation(pl);
		System.out.println(app.getID());
		ctrl = new DatabaseController();
		try {
		ctrl.saveAppointment(app);
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}//testSaveAppointment()
	
}
