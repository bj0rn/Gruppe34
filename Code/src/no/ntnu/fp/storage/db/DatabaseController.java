package no.ntnu.fp.storage.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseController {

	public static void main(String[] args) throws SQLException {
		//DatabaseController dbCtrl = new DatabaseController();
		
		//System.out.println(dbCtrl.authenticate("havard", "test"));
	}
	
	public DatabaseController() {
		
	}
	
	public boolean authenticate(String username, String password) throws SQLException {
		boolean authenticated = false; 

		Database db = new Database();
		
		ResultSet rs = db.query("SELECT count(*) FROM User WHERE Username = '" + username + "' AND Password = '" + password + "' ");
		
		
		if (rs.first()) {
			assert(rs.getInt(1) == 1);
			authenticated = true;
		}
		
		rs.close();
		db.close();
		
		return authenticated;
	}
	
	public boolean update(String type, int id, String key, String value) {
		
		
		
		return false;
	}
	
	//public int 
	
}
