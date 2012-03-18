package no.ntnu.fp.storage.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbConnection {
	
	
	//Fields 
	private String driver = "org.gjt.mm.mysql.Driver";
	private String host = "jdbc:mysql://mysql.stud.ntnu.no/batunges_calendarDB";
	private String user = "batunges_uber34";
	private String pass = "uber34";
	private Connection connect;
	
	//Constructor
	public DbConnection(Properties props){
		
		if (props != null) {
			driver = props.getProperty("jdbcDriver");
			host = props.getProperty("dbAddress");
			user = props.getProperty("user");
			pass = props.getProperty("password");
		}
		try {
			Class.forName(driver);
			connect = DriverManager.getConnection(host, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException sq){
			sq.printStackTrace();
		}	
	}
	
	/**
	 * Runs the {@code sql} query on the database and
	 * returns the {@code ResultSet}.
	 *  
	 * @param sql
	 * 		  The query to perform.
	 * 
	 * @return the result as a {@code ResultSet} 
	 * 
	 * @throws SQLException if a database access error occurs 
	 * 
	 */
	public ResultSet query(String sql) throws SQLException {
		Statement st = connect.createStatement();
		return st.executeQuery(sql);
	}
	
	public int executeUpdate(String sql) throws SQLException {
		Statement st = connect.createStatement();
		return st.executeUpdate(sql);
	}
	
	public void insert(Object obj){
		try{

			Class <? extends Object> clazz = obj.getClass();

			String classname = clazz.getSimpleName();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO "+ classname +"(");

			//Get field names
			Field[] fields = clazz.getDeclaredFields();
			boolean commaAdded = false;
			for (Field field : fields) {
				if(!commaAdded) {
					commaAdded = true;
				}
				else {
					sb.append(", "); 
				}
				sb.append(field.getName());
			}
			sb.append(") VALUES(");

			commaAdded = false;
			//Get field data
			for(Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);
				field.setAccessible(false);

				if(!commaAdded){
					commaAdded=true;
				}else{
					sb.append(", ");
				}
				sb.append(parseValue(value));
			}
			sb.append(")");

			String query1 = sb.toString();

			connect.createStatement().execute(query1);	

		}catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Create a String object of a inserted Object. Used by the generic
	 * insert function to manipulate data.
	 * @param value
	 * @return
	 */
	private Object parseValue(Object value) {
		return String.format("'%s'", value).toLowerCase();
	}
	/**
	 * C
	 * @param value
	 * @return
	 */
	private String parseValue(String value) {
		return String.format("'%s'", value);
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connect.close();
	}
	
	//methods
}
