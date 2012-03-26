package no.ntnu.fp.storage.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.CalendarEntry.CalendarEntryType;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.MeetingInviteNotification;
import no.ntnu.fp.model.MeetingReplyNotification;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;
import no.ntnu.fp.util.TimeLord;

/**
 * The {@code DatabaseController} serves as an interface between
 * the {@code ServerController} and the database. 
 * @author Håvard
 *
 */


public class DatabaseController {
	
	private Properties props;

	public static void main(String[] args) throws SQLException {
		DatabaseController dbCtrl = new DatabaseController();
		
		List<User> users = dbCtrl.getListOfUsers();
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	public DatabaseController() {
		props = new Properties();
		try {
			props.load(new FileInputStream(new File("Properties.properties")));
		} catch (FileNotFoundException e) {
			
			props = null;
			
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Authenticates a user based on the provided username and password.
	 * @author Håvard
	 * 
	 * @param username 
	 * 		  The username for the user to authenticate.
	 * 
	 * @param passwordHash
	 * 		  The hashed password for the user.
	 * 
	 * @return {@code true} if the parameters correspond to a valid user
	 * 		   {@code false} if they do not.
	 * 
	 * @throws SQLException if a database access error occurs 
	 * 		   
	 */
	
	public boolean authenticate(String username, String passwordHash) throws SQLException {
		boolean authenticated = false; 

		DbConnection db = getConnection();
		
		String sql = "SELECT count(*) FROM User WHERE Username = '" + username + "' AND Password = '" + passwordHash + "' ";
		ResultSet rs = db.query(sql);
		
		
		if (rs.first()) {
			int count = rs.getInt(1); 
			assert(count == 1 || count == 0); // the database should not contain two equal usernames.
			authenticated = count == 1;
		}
		
		rs.close();
		db.close();
		
		return authenticated;
	}
	
	/**
	 * Executes an update the value in the {@code key} column
	 * in the row with the {@code id} from the {@code table} 
	 * with the {@code value} provided.
	 * @author Håvard
	 * 
	 * @param table
	 * 		  the table to update
	 * 
	 * @param id
	 * 		  the id in the row
	 * 
	 * @param key
	 * 		  the column name
	 * 	
	 * @param value
	 * 		  the value to insert
	 * 	
	 * @return {@code true} if the update was successful,
	 * 		   {@code false} if not.
	 * 
	 * @throws SQLException if a database access error occurs 
	 */
	
	public boolean update(String table, int id, String key, String value) throws SQLException {
		
		DbConnection db = getConnection();
		
		String sql = "UPDATE " + table + " SET " + key + " = " + value + " WHERE id = " + id;
		
		int count = db.executeUpdate(sql);
		
		db.close();
		
		return count == 1;
	}
	
	/**
	 * Creates a new CalendarEntry of {@code type}.
	 * @author Håvard
	 * 
	 * @param type
	 * 		  the {@code CalendarEntryType} to create
	 * 
	 * @return the id of the created row
	 * 
	 * @throws SQLException if a database access error occurs 
	 */
	public int createCalendarEntry(CalendarEntryType type) throws SQLException {
		
		int id = -1;
		
		if (!(type.equals("Meeting")
		||	type.equals("Appointment")))
			throw new IllegalArgumentException();
		
		DbConnection db = getConnection();
		
		String sql = "INSERT INTO CalendarEntry () VALUES ();";
		
		db.executeUpdate(sql);
		
		ResultSet rs = db.query("SELECT LAST_INSERTED_ID");
		
		if(rs.first()) {
			id = rs.getInt(1);
		}
		
		rs.close();
		db.close();
		
		return id;		
	}
	
	/**
	 * Deletes a {@code CalendarEntry} from the database.
	 *  
	 * @author Håvard
	 * 
	 * @param id
	 * 		  the {@code CalendarEntry}s id in the database.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * 
	 * @throws SQLException if a database access error occurs 
	 */
	public boolean deleteCalendarEntry(int id) throws SQLException {
		
		DbConnection db = getConnection();
	
		int count = db.executeUpdate("DELETE FROM CalendarEntry WHERE id = " + id);
		
		db.close();
		
		return count == 1;
		
	}
	
	/**
	 * Gets a {@code List} of {@code User}s from the database.
	 * 
	 * @author Håvard
	 * 
	 * @return the {@code List} of {@code User}
	 * 
	 * @throws SQLException 
	 */
	public List<User> getListOfUsers() throws SQLException {
	
		List<User> result = new ArrayList<User>();
		
		DbConnection db = getConnection();
		
		String sql = "SELECT Username, Name, Age, PhoneNumber, Email FROM User";
		
		ResultSet rs = db.query(sql);
	
		rs.beforeFirst();
		while(rs.next()) {
			String username = rs.getString("Username");
			String name = rs.getString("Name");
			int age = rs.getInt("Age");
			int phoneNumber = rs.getInt("PhoneNumber");
			String email = rs.getString("Email");
			
			result.add(new User(username, name, age, phoneNumber, email));
		}
		
		return result;
		
	}
	
	/**
	 * Gets the full {@code User} with the {@code username}.
	 * The {@code User} contains a {@code Calendar} with
	 * all {@code CalendarEntry}s and a {@code List} of
	 * {@code Notification}s.
	 * 
	 * @author Håvard
	 * 
	 *  @param username
	 *  	   The {@code User}s username as a {@code String}.
	 *  
	 *  @return the {@code User} object.
	 * @throws SQLException 
	 */
	
	public User getFullUser(String username) throws SQLException {
		User user = null;
		
		DbConnection db = getConnection();
		
		String sql = "SELECT Username, Name, Age, PhoneNumber, Email FROM User WHERE Username = '" + username + "'";
		
		ResultSet rs = db.query(sql);
		
		if(rs.first()) {
			String uname = rs.getString("Username");
			String name = rs.getString("Name");
			int age = rs.getInt("Age");
			int phoneNumber = rs.getInt("PhoneNumber");
			String email = rs.getString("Email");
			
			user = new User(uname, name, age, phoneNumber, email);
			
			user.setCalendar(getCalendar(user));
		}
		
		return user;
	}
	
	/**
	 * Gets the {@code Calendar} for the given {@code User}.
	 * The {@code Calendar} will contain a {@code List} of
	 * {@code CalendarEntry}s.
	 * 
	 * @author Håvard
	 * 
	 * @param user 
	 * 		  the {@code User} to pull the {@code Calendar} for
	 * 
	 * @return the {@code Calendar} for the {@code User}
	 * @throws SQLException 
	 */
	
	public Calendar getCalendar(User user) throws SQLException {
		
		Calendar calendar = new Calendar(user);
		
		String username = user.getUsername();
		
		String sql = 
			"SELECT " 
		+	"	OWNCA.Username as owner, "
		+	"	CE.CalendarEntryID AS id, "
		+	"	CE.EntryType AS type, " 
		+	"	CE.TimeStart AS start, " 
		+	"	CE.TimeEnd AS end, " 
		+	"	CE.Description AS description, " 
		+	"	L.LocationID AS LocationID "
		+	"FROM Calendar AS C "
		+	"	LEFT JOIN Contains AS CO ON CO.CalendarID = C.CalendarID "
		+	"	LEFT JOIN CalendarEntry AS CE ON CO.CalendarEntryID = CE.CalendarEntryID "
		+	"	LEFT JOIN Location AS L ON CE.LocationID = L.LocationID "
		+	"	LEFT JOIN Contains AS OWNCO ON CE.CalendarEntryID = OWNCO.CalendarEntryID " 
		+	"		AND OWNCO.Role = 'Owner' "
		+	"	LEFT JOIN Calendar AS OWNCA ON OWNCO.CalendarID = OWNCA.CalendarID "
		+	"WHERE C.Username = '" + username + "'";

		System.out.println(sql);
		
		DbConnection db = getConnection();
		
		ResultSet rs = db.query(sql);
		
		rs.beforeFirst();
		while(rs.next()) {
			String owner = rs.getString("owner");
			int id = rs.getInt("id");
			String type = rs.getString("type");
			Date start = rs.getDate("start");
			Date end = rs.getDate("end");
			String desc = rs.getString("description");
			int locationID = rs.getInt("LocationID");
			
			CalendarEntry entry = null;
			System.out.println(type == null);
			if (type.equals(CalendarEntry.MEETING)) {
				Meeting meeting = new Meeting(start, end, desc, id);
				
				Map<User, State> participants = getParticipants(id);
				meeting.addParticipants(participants);
				
				entry = meeting;
				
			} else {
				assert(type.equals(CalendarEntry.APPOINTMENT)); // the database should only contain two types
				entry = new Appointment(start, end, desc, id);
			}
			
			entry.setOwner(new User(owner));
			
			Location location = getLocation(locationID);
			entry.setLocation(location);
			
			calendar.addCalendarEntry(entry);
		}
		
		db.close();
		rs.close();
		
		return calendar;
	}

	/**
	 * Gets a {@code Location} from the database
	 * 
	 * @author Håvard
	 * 
	 * @param locationID
	 * 		  the row id for the {@code Location}
	 * 
	 * @return the {@code Location} or {@code null} if it does not exists.
	 * @throws SQLException 
	 */
	private Location getLocation(int locationID) throws SQLException {
		
		Location result = null;
		
		DbConnection db = getConnection();
		
		String sql = "SELECT RoomName, Description, Capacity FROM Room WHERE LocationID = " + locationID;
		
		ResultSet rs = db.query(sql);
		
		if (rs.first()) {
			String name = rs.getString("RoomName");
			String desc = rs.getString("Description");
			int capacity = rs.getInt("Capacity");
			
			result = new Room(locationID, name, desc, capacity);
			
		} else  {
			
			sql = "SELECT LocationID, Description FROM Place WHERE LocationID = " + locationID;
			rs = db.query(sql);
			
			if (rs.first()) {
				
				String desc = rs.getString("Description");
				
				result = new Place(locationID, desc);	
			}	
		}
		
		db.close();
		rs.close();
		
		return result;
		
	}
	
	/**
	 * Gets a {@code Map} with all the participants to the {@code Meeting}
	 * The {@code Map} is from {@code User} to {@code State}. 
	 * 
	 * @param meetingID
	 * @return
	 * @throws SQLException
	 */
	
	private Map<User, State> getParticipants(int meetingID) throws SQLException {
		
		Map<User, State> result = new HashMap<User, State>();
		
		DbConnection db = getConnection();
		
		
		String sql = 
			"SELECT "
		+	"	U.Username AS user, " 
		+	"	U.Name AS name, "
		+	"	U.Age AS age, "
		+	"	U.PhoneNumber AS number, "
		+	"	U.Email AS email, "
		+	"	CO.State AS state "
		+	"FROM Contains AS CO "
		+	"	LEFT JOIN Calendar AS CA ON CO.CalendarID = CA.CalendarID "
		+	"	LEFT JOIN User U ON CA.Username = U.Username "
		+	"WHERE CO.Role = 'Participant' " 
		+	"	AND CO.CalendarEntryID = " + meetingID;
		
		ResultSet rs = db.query(sql);
		
		rs.beforeFirst();
		while(rs.next()) {
			
			String uname = rs.getString("user");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			int phoneNumber = rs.getInt("number");
			String email = rs.getString("email");
			
			String state = rs.getString("state");
			
			User user = new User(uname, name, age, phoneNumber, email);
			
			State s = State.getState(state);
		
			result.put(user, s);
			
		}
		
		db.close();
		rs.close();
		
		return result;
	}

	/**
	 * Gets a {@code List} of {@code Location} from the database.
	 * Will a situation in which the system needs a list of all
	 * rooms AND places?
	 * 
	 * @return the {@code List} of {@code Location}
	 * 
	 * @throws SQLException
	 */
	public List<Location> getListOfLocations() throws SQLException {
		
		List<Location> locs = new ArrayList<Location>();
		DbConnection dbc = getConnection();
		//hm so first get the rooms I assume, then the 'places'
		String sqlR = "SELECT RoomName, Description, Capacity FROM Room";
		ResultSet rs = dbc.query(sqlR);
		rs.beforeFirst();
		
		while(rs.next()) {
			
		}
		
		//String sqlP = "SELECT LocationID, Description FROM Place";
		return null;
	}
	
	/**
	 * Gets a {@code List} of {@code Notification} for the {@code User}
	 * from the database.
	 * wait how does one do this
	 *  
	 * @param user
	 * 		  the {@code User} to get the {@code List} of {@code Notification}s for. 
	 * 
	 * @return a {@code List} of {@code Notification}s for the {@code User}
	 */
	public List<Notification> getListOfNotifications(String username) throws SQLException {
		
		List<Notification> res = new ArrayList<Notification>();
			
		List<MeetingInviteNotification> invites = getListOfMeetingInviteNotifications(username);
		List<MeetingReplyNotification> replies = getListOfMeetingReplyNotifications(username);
		
		res.addAll(invites);
		res.addAll(replies);
		
		return res;
	}
	
	private List<MeetingInviteNotification> getListOfMeetingInviteNotifications(String username) throws SQLException {
		
		List<MeetingInviteNotification> invites = new ArrayList<MeetingInviteNotification>();
		
		DbConnection db = getConnection();
		
		String sql = 
			"SELECT"
		+	"	U.Username AS user, "
		+	"	CO.State AS state, "
		+	"	CE.CalendarEntryID meeting "
		+	"FROM User U "
		+	"	JOIN Calendar AS CA ON U.Username = CA.Username "
		+	"	LEFT JOIN Contains AS CO ON CA.CalendarID = CO.CalendarID "
		+	"	LEFT JOIN CalendarEntry AS CE ON CO.CalendarEntryID = CE.CalendarEntryID "
		+	"WHERE U.Username = '"+ username+"' "
		+	"	AND CO.State = 'Pending' "
		+	"	AND CO.Role = 'Participant'";
		
		ResultSet rs = db.query(sql);
		
		rs.beforeFirst();
		while(rs.next()) {
			
			String uname = rs.getString("user");
			//String state = rs.getString("state");
			int mid = rs.getInt("meeting");
			
			MeetingInviteNotification notification = new MeetingInviteNotification(new User(uname), new Meeting(mid));
		
			invites.add(notification);
		}
		
		return invites;
	}

	private List<MeetingReplyNotification> getListOfMeetingReplyNotifications(String username) throws SQLException {
		
		List<MeetingReplyNotification> replies = new ArrayList<MeetingReplyNotification>();
		
		DbConnection db = getConnection();
		
		String sql = 
			"SELECT "
		+	"	AU.Username AS user, "
		+	"	BCO.State state, "
		+	"	ACE.CalendarEntryID meeting "
		
		+	"FROM User AU "
		+	"JOIN Calendar ACA ON AU.Username = ACA.Username "
		+	"LEFT JOIN Contains ACO ON ACA.CalendarID = ACO.CalendarID "
		+	"LEFT JOIN CalendarEntry AS ACE ON ACO.CalendarEntryID = ACE.CalendarEntryID "

		+	"LEFT JOIN Contains BCO ON ACE.CalendarEntryID = BCO.CalendarEntryID "
		+	"LEFT JOIN Calendar BCA ON BCO.CalendarID = BCA.CalendarID "
		+	"LEFT JOIN User BU ON BCA.Username = BU.Username "

		+	"WHERE AU.Username = '" + username + "' "
		+	"AND ACO.Role = 'Owner' "
		+	"AND BCO.Role = 'Participant' "
		+	"AND BCO.State = 'Rejected'";
		
		ResultSet rs = db.query(sql);
		
		rs.beforeFirst();
		while (rs.next()) {
			String uname = rs.getString("user");
			//String state = rs.getString("state");
			int mid = rs.getInt("meeting");
			
			MeetingReplyNotification notification = new MeetingReplyNotification(new User(uname), new Meeting(mid));
			
			replies.add(notification);
		}
		
		return replies;
	}
	
	/**
	 * 
	 * @param username
	 * @param meeting_id
	 * @param state
	 * @return
	 * @throws SQLException
	 */
	public boolean updateMeetingState(String username, String meeting_id, State state) throws SQLException {
		
		DbConnection db = getConnection();
		
		String sql = "UPDATE Contains SET State = '" + state + "' WHERE CalendarID = (SELECT CalendarID FROM Calendar WHERE Username = '"+username+"') AND CalendarEntryID = " + meeting_id;
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}

	/**
	 * Saves a {@code User} to the database.
	 * A non-existing {@code User} will be created,
	 * an existing will be updated
	 * hmm, where should we be creating the salt? 
	 *
	 * 
	 * @param user
	 * 		  the {@code User} to created/change
	 * 
	 * @return the {@code User}s database id
	 * What? Users ids are their usernames!
	 */
	public String saveUser(User user) throws SQLException {
		DbConnection dbc = getConnection();
		String sqlSelUsr = "SELECT count(*) AS ROW FROM User WHERE Username='"+user.getUsername()+"'"; 
		ResultSet rs = dbc.query(sqlSelUsr);
		String sql = "";
		//find out if we've gotten more than one username from this
		//-- why do we need to do that this shouldn't happen AT ALL
		int i = 0;
		if(rs.first()) {
			while (rs.next()) {
				i++;
			}
		}
		if (i == 1) {
			//update the one existing user.
			sql = "UPDATE User SET "
				 +"Password='"+ user.getPassword() + "', "
				 +"Name='"+ user.getName() +"', "
				 +"Age="+ user.getAge() +"', "
				 +"PhoneNumber="+ user.getPhoneNumber() +"', "
				 +"Email='"+ user.getEmail() +"' "
				 +"WHERE Username='"+ user.getUsername()+"'";
			dbc.executeUpdate(sql);
			return user.getUsername();
		}
		if (i == 0) {
			sql = "INSERT INTO User (Username, Password, Name, " +
					"Age, PhoneNumber, Email) VALUES("
					+ user.getUsername() + ", "
					+ user.getPassword() + ", "
					+ user.getName() + ", "
					+ user.getAge() + ", " 
					+ user.getPhoneNumber() + ", "
					+ user.getEmail() + ")";
			return getLastInsertedID("User", dbc);
		}
		//if (i > 1) {
			//so what is exactly supposed to happen here?
			//What kind of measures are we supposed to take when
			//we've broken our own restraints?
			//I guess... return?
			return null;
		
		//herp a derp don't look here.
//		//IF EXISTS doesn't work for us.
//		String s = ""
//			+ "IF EXISTS( SELECT * FROM User WHERE Username="
//			+ user.getUsername() + ") BEGIN UPDATE User"
//			+ "SET Password=" + user.getPassword()
//			+ ", Name=" + user.getName()
//			+ ", Age=" + user.getAge()
//			+ ", PhoneNumber=" + user.getPhoneNumber()
//			+ ", Email=" + user.getEmail()
//			+ " WHERE Username=" + user.getUsername()
//			+ "END ELSE "
//			//Below a new user is created.
//			+ "BEGIN INSERT INTO User (Username, Password, Name, " +
//			"Age, PhoneNumber, Email) VALUES("
//			+ user.getUsername() + ", "
//			+ user.getPassword() + ", "
//			+ user.getName() + ", "
//			+ user.getAge() + ", " 
//			+ user.getPhoneNumber() + ", "
//			+ user.getEmail() + ")"
//			+" END";
//		
//		
//		dbc.query(s); //Since we're just updating/inserting there's no need for the result set, right?
//		return user.getUsername();
	}
	
	
	/**
	 * Saves a {@code Appointment} to the database.
	 * A non-existing {@code Appointment} will be created,
	 * an existing will be updated
	 * it is _REALLY_ hard to check whether or not we need to update
	 * an existing appointment or create a new one when we do not have
	 * the argument Appointment's ID.
	 * 
	 * @param user
	 * 		  the {@code Appointment} to created/change
	 * 
	 * @return the {@code Appointment}s database id
	 */
	public int saveAppointment(Appointment appointment) throws SQLException {
		DbConnection dbc = getConnection();
		String sql = "";
		if (appointment.getID() == -1) { //need to create a new appointment k
			sql = "INSERT INTO CalendarEntry (TimeStart, TimeEnd, TimeCreated"+
				", Description, EntryType, LocationID) VALUES ('" +
				TimeLord.changeDateToSQL(appointment.getStartDate()) +"', '"+
				TimeLord.changeDateToSQL(appointment.getEndDate()) +"', "+
				"NOW(), '" + appointment.getDescription() + "', '" +
				CalendarEntryType.APPOINTMENT + "', " + 
				appointment.getLocation().getID() + ")";
			dbc.executeUpdate(sql);
			//System.out.println(sql);
			String s = "SELECT DISTINCT LAST_INSERT_ID() AS ID FROM CalendarEntry";
			ResultSet rs = dbc.query(s);
			//System.out.println("herp");
		if(rs.first())
			return rs.getInt("ID");
		}
		
		
		
		//ok so the thing exists in the database.
		sql = "UPDATE CalendarEntry SET TimeStart='" +
				TimeLord.changeDateToSQL(appointment.getStartDate())+"', "+
				"TimeEnd='"+TimeLord.changeDateToSQL(appointment.getEndDate())+
				"', Description='"+appointment.getDescription()+"', "+
				"LocationID="+appointment.getLocation().getID()+
				" WHERE CalendarEntryID="+appointment.getID();
		dbc.executeUpdate(sql);
		System.out.println("derp");
		return appointment.getID();
	}
	
	/**
	 * Saves a {@code Meeting} to the database.
	 * A non-existing {@code Meeting} will be created,
	 * a existing will be updated
	 * 
	 * @param user
	 * 		  the {@code Meeting} to created/change
	 * 
	 * @return the {@code Meeting}s database id
	 */
	public int saveMeeting(Meeting meeting) throws SQLException {
		DbConnection dbc = getConnection();
		String sql = "";
		if (meeting.getID() == -1) { //it's a brand new meeting.
			sql = "INSERT INTO CalendarEntry (TimeStart, TimeEnd, TimeCreated"+
				", Description, EntryType, LocationID) VALUES('"+
				TimeLord.changeDateToSQL(meeting.getStartDate())+"', '"+
				TimeLord.changeDateToSQL(meeting.getEndDate())+"', "+
				"NOW(), '"+meeting.getDescription()+"', '"+
				CalendarEntryType.MEETING+"', "+
				meeting.getLocation().getID()+")";
			dbc.executeUpdate(sql);
			String s = "SELECT DISTINCT LAST_INSERT_ID() AS ID FROM CalendarEntry";
			ResultSet rs = dbc.query(s);
			dbc.close();
			if (rs.first())
				return rs.getInt("ID");
		}//end new meeting
		
		//did we get here? okay, we need to update an existing meeting.
		sql = "UPDATE CalendarEntry SET TimeStart='" +
				TimeLord.changeDateToSQL(meeting.getStartDate())+"', "+
				"TimeEnd='"+TimeLord.changeDateToSQL(meeting.getEndDate())+
				"', Description='"+meeting.getDescription()+"', "+
				"LocationID="+meeting.getLocation().getID()+
				" WHERE CalendarEntryID="+meeting.getID();
		dbc.executeUpdate(sql);
		dbc.close();
		return meeting.getID();
	}
	
	/**
	 * Saves a {@code Room} to the database.
	 * A non-existing {@code Room} will be created,
	 * a existing will be updated.
	 * Currently allowing for room names to be changed post-creation.
	 * 
	 * @param user
	 * 		  the {@code Room} to created/change
	 * 
	 * @return the {@code Room}s database id
	 * 
	 */	
	public int saveRoom(Room room) throws SQLException {
		//the primary key of a room is its name
		//it also has a location ID
		DbConnection dbc = getConnection();
		String sql = "";
		if (room.getName() == null || room.getID() == -1) {
			int locID = createLocation(dbc);
			//SQL-query string
			sql = "INSERT INTO Room (RoomName, Description, Capacity, LocationID) "
				 +" VALUES('" + room.getName() + "', '" + room.getDescription() +"', '"
				 +room.getCapacity() + "', " + locID + ")";
			dbc.executeUpdate(sql);
			return locID;
		}//end new room
		//update existing room
		sql = "UPDATE Room SET "
			 +"RoomName='"+room.getName()+"', "
			 +"Description='"+room.getDescription()+"', "
			 +"Capacity="+room.getCapacity()
			 +"WHERE LocationID="+room.getID()+" AND RoomName='"+room.getName()+"'";
		return room.getID();
	}
	/**
	 * Saves a {@code Place} to the database.
	 * A non-existing {@code Place} will be created,
	 * a existing will not be touched.
	 * 
	 * @param user
	 * 		  the {@code Place} to created/change
	 * 
	 * @return the {@code Place}s database id
	 */
	public int savePlace(Place place) throws SQLException {
		DbConnection dbc = getConnection();
		String sql = "";
		if (place.getID() == -1) {//begin new place
			int locID = createLocation(dbc);
			sql = "INSERT INTO Place (Description, LocationID) VALUES ("
			     +"Description='"+place.getDescription()+"', "
			     +"LocationID()="+locID+")";
		}//end new place
		//begin update of existing place
		sql = "UPDATE Place SET "
			 +"Description='"+place.getDescription()+"' "
			 +"WHERE LocationID="+place.getID();
		dbc.executeUpdate(sql);
		return place.getID();
	}
	/**
	 * Creates a new location in the database through the given connection
	 * and returns its ID.
	 * 
	 * @return
	 * 		Returns the ID of the newly created location.
	 */
	private int createLocation(DbConnection dbc) throws SQLException {
		String sql = "INSERT INTO Location VALUES ()";
		dbc.executeUpdate(sql);
		return Integer.parseInt(getLastInsertedID("Location", dbc));
	}
	
	/**
	 * Deletes the {@code User} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code User}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * @throws SQLException 
	 */
	public boolean deleteUser(String username) throws SQLException {
		DbConnection db = getConnection();
		
		String sql = "DELETE FROM User WHERE Username = '" + username + "'";
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}
	
	/**
	 * Deletes the {@code Appointment} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Appointment}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * @throws SQLException 
	 */
	public boolean deleteAppointment(int id) throws SQLException {
		DbConnection db = getConnection();
		
		String sql = "DELETE FROM CalendarEntry WHERE CalendarEntryId = " + id;
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}

	/**
	 * Deletes the {@code Meeting} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Meeting}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * @throws SQLException 
	 */
	public boolean deleteMeeting(int id) throws SQLException {
		
		DbConnection db = getConnection();
		
		String sql = "DELETE FROM CalendarEntry WHERE CalendarEntryId = " + id;
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}

	/**
	 * Deletes the {@code Room} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Room}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * @throws SQLException 
	 */
	public boolean deleteRoom(int id) throws SQLException {
		DbConnection db = getConnection();
		
		String sql = "DELETE FROM Location WHERE LocationID = " + id;
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}

	/**
	 * Deletes the {@code Place} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Place}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 * @throws SQLException 
	 */
	public boolean deletePlace(int id) throws SQLException {
		DbConnection db = getConnection();
		
		String sql = "DELETE FROM Location WHERE LocationID = " + id;
		
		int n = db.executeUpdate(sql);
		
		db.close();
		
		return n == 1;
	}
	
	/**
	 * Creates a {@code DbConnection} to the database.
	 * 
	 * @author Håvard
	 * 
	 * @return a {@code DbConnection} to the database.
	 */
	public DbConnection getConnection() {
		return new DbConnection(props);
	}
	/**
	 * Gets the ID of the last inserted element in the given table.
	 * @param table
	 * 		  the name of the table to get the last inserted ID from
	 * @return
	 * 		  the last inserted ID in the given table
	 */
	public String getLastInsertedID(String table, DbConnection dbc) throws SQLException {
		String result = "-1";
		String sql = "SELECT DISTINCT LAST_INSERT_ID() AS ID FROM " + table +";";
		ResultSet rs = dbc.query(sql);
		if (rs.first()) {
			result = rs.getString("ID");
		}
		dbc.close();
		return result;
	}
	
}
