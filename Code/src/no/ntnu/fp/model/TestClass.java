package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.List;

public class TestClass {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub

		TestPerson t = new TestPerson("Ola", 25, "test@test.com");
		TestPerson t1 = new TestPerson("Per", 45, "test@test.com");
		TestPerson t3 = new TestPerson("Bj¿rn", 23, "meister.bj0rn@gmail.com");

		TestPersonList list = new TestPersonList();
		list.addTestPerson(t);
		list.addTestPerson(t1);
		list.addTestPerson(t3);
		
		List <TestPerson> testList = new ArrayList<TestPerson>();
		testList.add(t);
		testList.add(t1);
		testList.add(t3);
		
		
		GenericXmlSerializer xml = new GenericXmlSerializer();
		
		
		System.out.println(xml.isIterable(list));

		
		//String test = xml.toXmlSimple(list);

		//System.out.println(test);
		
		//System.out.println("Grrr: "+xml.toXmlSimple(testList));

		//String mockmodel1 = "<no.ntnu.fp.model.MockModel id=\"1\" type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age></no.ntnu.fp.model.MockModel>";
		//String mockmodel2 = "<no.ntnu.fp.model.MockModel type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age><no.ntnu.fp.model.MockModel type=\"ref\" id =\"1\" /></no.ntnu.fp.model.MockModel>";

		//String xml2 = "<no.ntnu.fp.model.MockListModel type=\"object\">" + mockmodel1 + mockmodel2 + "</no.ntnu.fp.model.MockListModel>" ;

		//System.out.println(xml.toXml(t));

		//System.out.println(xml2);
	}

}

