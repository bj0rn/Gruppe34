package no.ntnu.fp.model;

public class MockModel {

	private String name;
	
	private int age;
	
	public MockModel() {
		
	}
	
	public MockModel(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAge(String age) {
		this.age = Integer.parseInt(age);
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return age;
	}
	
	public String toString() {
		return name + ' ' + age;
	}
	
}
