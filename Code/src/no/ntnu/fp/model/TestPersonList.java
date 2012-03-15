package no.ntnu.fp.model;

import java.util.ArrayList;

public class TestPersonList {
	private ArrayList<TestPerson> personList;
	
	public TestPersonList(){
		personList = new ArrayList<TestPerson>();
	}
	
	public void addTestPerson(TestPerson person){
		personList.add(person);
	}
	
}
