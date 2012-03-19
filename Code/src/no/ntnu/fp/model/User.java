package no.ntnu.fp.model;

import java.beans.PropertyChangeSupport;
import java.util.List;


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
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
 	public final static String UNAME_PROPERT ="Username";
 	public final static String PWORD_PROPERT ="Password";
 	public final static String NAME_PROPERT ="Name";
 	public final static String AGE_PROPERT ="Age";
 	public final static String PNUMBER_PROPERT ="Phonenumber";
 	public final static String EMAIL_PROPERT ="Email";
 	public final static String CAL_PROPERT ="Calendar";

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username){
		String oldValue = this.username;
		this.username = username;
		pcs.firePropertyChange(UNAME_PROPERT, oldValue, username);
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		String oldValue = this.password;
		this.password = password;
		pcs.firePropertyChange(PWORD_PROPERT, oldValue, password);
	}
	public String getPassword(){
		return password;
	}
	
	public void addNotification(Notification notification){
		notifications.add(notification);
		
	}
	
	public void removeNotification(Notification notification){
		notifications.remove(notification);
	}
	
	public Calendar setCalendar(Calendar calendar){
		String oldValue = this.calendar;
		this.calendar = calendar;
		pcs.firePropertyChange(CAL_PROPERT, oldValue, calendar);
	} 
	
	public Calendar getCalendar(){
		return calendar;
	}
	
	public void setName(String name){
		String oldValue = this.name;
		this.name = name;
		pcs.firePropertyChange(NAME_PROPERT, oldValue, name);
	}
	public String getName(){
		return name;
	}
	
	public void setAge(int age){
		int oldValue = this.age;
		this.age = age;
		pcs.firePropertyChange(AGE_PROPERT, oldValue, age);
	}
	public int getAge(){
		return age;
	}
	
	public void setPhoneNumber(String phoneNumber){
		String oldValue = this.phoneNumber;
		this.phoneNumber = phoneNumber;
		pcs.firePropertyChange(PNUMBER_PROPERT, oldValue, phoneNumber);
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setEmail(String email){
		String oldValue = this.email;
		this.email = email;
		pcs.firePropertyChange(EMAIL_PROPERT, oldValue, email);
	}
	public String getEmail(){
		return email;
	}

}
