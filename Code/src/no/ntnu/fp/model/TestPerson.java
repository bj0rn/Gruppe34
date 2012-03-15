package no.ntnu.fp.model;

import java.util.ArrayList;

public class TestPerson {
	private String name;
	private int age;
	private String email;
	
	public TestPerson(String name, int age, String email){
		this.name = name;
		this.age = age;
		this.email = email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
}
