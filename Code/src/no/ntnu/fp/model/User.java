package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User extends Model implements Serializable, Comparable, PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String name;
	private int age;
	private int phoneNumber;
	private String email;
	
	private ModelChangeListener modelChangeListener;
	private Calendar calendar = new Calendar(this);
	private List<Notification> notifications = new ArrayList<Notification>();
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
 	public final static String UNAME_PROPERT ="Username";
 	public final static String PWORD_PROPERT ="Password";
 	public final static String NAME_PROPERT ="Name";
 	public final static String AGE_PROPERT ="Age";
 	public final static String PNUMBER_PROPERT ="Phonenumber";
 	public final static String EMAIL_PROPERT ="Email";
 	public final static String CAL_PROPERT ="Calendar";

 	public User() {}
 	
 	public User(String username) {
 		this.username = username;
 	}
 	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String name, int age, int phoneNumber,	String email) {
		this.username = username;
		this.name = name;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public String getId() {
		return username;
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
	
	public List<Notification> getNotifications() {
		return calendar.getMeetingNotifications();
	}
	
	public void setCalendar(Calendar calendar){
		Calendar oldValue = this.calendar;
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
	
	public void setAge(String age) {
		setAge(Integer.parseInt(age));
	}
	
	public void setAge(int age){
		int oldValue = this.age;
		this.age = age;
		pcs.firePropertyChange(AGE_PROPERT, oldValue, age);
	}
	public int getAge(){
		return age;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		setPhoneNumber(Integer.parseInt(phoneNumber));
	}
	
	public void setPhoneNumber(int phoneNumber){
		int oldValue = this.phoneNumber;
		this.phoneNumber = phoneNumber;
		pcs.firePropertyChange(PNUMBER_PROPERT, oldValue, phoneNumber);
	}
	public int getPhoneNumber(){
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

	public String toString() {
		return 	"Username: " + username + "\n" 
		+ 		"Name: " + name + "\n" 
		+ 		"Age: " + age + "\n" 
		+ 		"PhoneNumber: " + phoneNumber + "\n"
		+		"Email: " + email;
	}
	
	public static void main(String[] args) {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User)obj;
			return username.equals(user.username);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof User) {
			username.compareTo(((User)o).username);
		}
		return -1;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == calendar) {
			pcs.firePropertyChange(evt);
		}
	}
	
}
