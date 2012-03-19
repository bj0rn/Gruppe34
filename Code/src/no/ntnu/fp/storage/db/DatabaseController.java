package no.ntnu.fp.storage.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.CalendarEntry.CalendarEntryType;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Notification;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.model.User;

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
		
		System.out.println(dbCtrl.authenticate("havard", "test"));
	}
	
	public DatabaseController() {
		props = new Properties();
		try {
			props.load(new FileInputStream(new File("Properties.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			assert(count == 1 || count == 0);
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
	 * @return the {@code List} of {@code User}
	 */
	public List<User> getListOfUsers() {
		return null;
	}
	
	/**
	 * Gets the full {@code User} with the {@code username}.
	 * The {@code User} contains a {@code Calendar} with
	 * all {@code CalendarEntry}s and a {@code List} of
	 * {@code Notification}s.
	 * 
	 *  @param username
	 *  	   The {@code User}s username as a {@code String}.
	 *  
	 *  @return the {@code User} object.
	 */
	
	public User getFullUser(String username) {
		return null;
	}
	
	/**
	 * Gets the {@code Calendar} for the given {@code User}.
	 * The {@code Calendar} will contain a {@code List} of
	 * {@code CalendarEntry}s.
	 * 
	 * @param user 
	 * 		  the {@code User} to pull the {@code Calendar} for
	 * 
	 * @return the {@code Calendar} for the {@code User}
	 */
	
	public Calendar getCalendar(User user) {
		return null;
	}
	
	/**
	 * Gets a {@code List} of {@code Location} from the database.
	 * 
	 * @return the {@code List} of {@code Location}
	 */
	public List<Location> getListOfLocations() {
		return null;
	}
	
	/**
	 * Gets a {@code List} of {@code Notification} for the {@code User}
	 * from the database.
	 * 
	 * @param user
	 * 		  the {@code User} to get the {@code List} of {@code Notification}s for. 
	 * 
	 * @return a {@code List} of {@code Notification}s for the {@code User}
	 */
	public List<Notification> getListOfNotifications(User user) {
		return null;
	}
	
	/**
	 * Saves a {@code User} to the database.
	 * A non-existing {@code User} will be created,
	 * a existing will be updated
	 * 
	 * @param user
	 * 		  the {@code User} to created/change
	 * 
	 * @return the {@code User}s database id
	 */
	public int saveUser(User user) {
		return 0;
	}
	
	/**
	 * Saves a {@code Appointment} to the database.
	 * A non-existing {@code Appointment} will be created,
	 * a existing will be updated
	 * 
	 * @param user
	 * 		  the {@code Appointment} to created/change
	 * 
	 * @return the {@code Appointment}s database id
	 */
	public int saveAppointment(Appointment appointment) {
		return 0;
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
	public int saveMeeting(Meeting meeting) {
		return 0;
	}
	
	/**
	 * Saves a {@code Room} to the database.
	 * A non-existing {@code Room} will be created,
	 * a existing will be updated
	 * 
	 * @param user
	 * 		  the {@code Room} to created/change
	 * 
	 * @return the {@code Room}s database id
	 */	
	public int saveRoom(Room room) {
		return 0;
	}
	
	/**
	 * Saves a {@code Place} to the database.
	 * A non-existing {@code Place} will be created,
	 * a existing will be updated
	 * 
	 * @param user
	 * 		  the {@code Place} to created/change
	 * 
	 * @return the {@code Place}s database id
	 */
	public int savePlace(Place place) {
		return 0;
	}
	
	/**
	 * Deletes the {@code User} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code User}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 */
	public boolean deleteUser(int id) {
		return false;
	}
	
	/**
	 * Deletes the {@code Appointment} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Appointment}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 */
	public boolean deleteAppointment(int id) {
		return false;
	}

	/**
	 * Deletes the {@code Meeting} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Meeting}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 */
	public boolean deleteMeeting(int id) {
		return false;
	}

	/**
	 * Deletes the {@code Room} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Room}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 */
	public boolean deleteRoom(int id) {
		return false;
	}

	/**
	 * Deletes the {@code Place} with the given id from the database.
	 * 
	 * @param id
	 * 		  the {@code Place}s database id.
	 * 
	 * @return {@code true} if the delete is successful,
	 * 		   {@code false} if not.
	 */
	public boolean deletePlace(int id) {
		return false;
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
}
