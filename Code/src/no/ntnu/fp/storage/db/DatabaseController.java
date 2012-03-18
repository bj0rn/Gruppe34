package no.ntnu.fp.storage.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import no.ntnu.fp.model.CalendarEntry.CalendarEntryType;

/**
 * The {@code DatabaseController} servers as an interface between
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
	 * @param password
	 * 		  The hashed password for the user.
	 * 
	 * @return {@code true} if the parameters correspond to a valid user
	 * 		   {@code false} if they do not.
	 * 
	 * @throws SQLException if a database access error occurs 
	 * 		   
	 */
	
	public boolean authenticate(String username, String password) throws SQLException {
		boolean authenticated = false; 

		DbConnection db = getConnection();
		
		String sql = "SELECT count(*) FROM User WHERE Username = '" + username + "' AND Password = '" + password + "' ";
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
	 * 
	 * @author Håvard
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public boolean deleteCalendarEntry(int id) throws SQLException {
		
		DbConnection db = getConnection();
		
		int count = db.executeUpdate("DELETE FROM CalendarEntry WHERE id = " + id);
		
		db.close();
		
		return count == 1;
		
	}
	
	//public int 
	
	/**
	 * 
	 * @author Håvard
	 * @return
	 */
	public DbConnection getConnection() {
		return new DbConnection(props);
	}
}
