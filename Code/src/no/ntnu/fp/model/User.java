package no.ntnu.fp.model;


public class User {
	private String username;
	private String password;
	private String name;
	private int age;
	private String phoneNumber;
	private String email;
	
	private ModelChangeListener modelChangeListener;
	private Calendar calendar;
	private List<Notification> notifications;
 	

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	
	public void addNotification(Notification notification){
		
	}
	
	public void removeNotification(Notification notification){
		
	}
	
	public Calendar setCalendar(Calendar calendar){
		this.calendar = calendar;
	} 
	
	public Calendar getCalendar(){
		return calendar;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	public int getAge(){
		return age;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}

}
