package no.ntnu.fp.storage.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	public static void main(String[] args) {
		Database db = new Database();
		
	}
	
	
	//Fields 
	private String driver = "org.gjt.mm.mysql.Driver";
	private String host = "jdbc:mysql://mysql.stud.ntnu.no/batunges_data";
	private String user = "batunges_uber34";
	private String pass = "uber34";
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

	
	
	public ResultSet query(String sql) throws SQLException {
		Statement st = connect.createStatement();
		return st.executeQuery(sql);
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
	
	
	public void close() throws SQLException {
		connect.close();
	}
	
	//methods
}
