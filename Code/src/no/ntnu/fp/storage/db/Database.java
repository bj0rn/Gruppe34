package no.ntnu.fp.storage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database {
	//Fields 
	private String driver = "org.gjt.mm.mysql.Driver";
	private String host = "jdbc:mysql://mysql.stud.ntnu.no/batunges_data";
	private String user = "batunges_uber13";
	private String pass = "uber13";
	private Connection connect;
	
	//Constructor
	public Database(){
		try {
			Class.forName(driver);
			connect = DriverManager.getConnection(host, user, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException sq){
			sq.printStackTrace();
		}
		
		
	}
		
	public void insert(Object obj){
		
	}
	
	
	
	
	//methods
}
