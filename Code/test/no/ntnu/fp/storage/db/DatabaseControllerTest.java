package no.ntnu.fp.storage.db;

import java.sql.SQLException;

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
	
}
