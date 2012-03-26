package no.ntnu.fp.gui.timepicker;

import java.util.Date;

import no.ntnu.fp.util.TimeLord;

import junit.framework.TestCase;

public class DateModelTest extends TestCase {

	public void testStartDay() {
		
		Date date = new Date(2012, 0, 1, 12, 00);
		DateModel model = new DateModel(date);
		assertEquals(6, model.getMonthStartDay());
		
		// Februar leapyear
		date = new Date(2012, 1, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(2, model.getMonthStartDay());
		
		// March
		for (int i=1; i<= 31; i++) {
			date = new Date(2012, 2, i, 11, 39);
			System.out.println(TimeLord.formatDate(date));
			model = new DateModel(date);
			int startday = model.getMonthStartDay();
			System.out.println(startday);
			assertEquals(3, startday);
		}
		
		
		// April
		date = new Date(2012, 3, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(6, model.getMonthStartDay());
		
		// May
		date = new Date(2012, 4, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(1, model.getMonthStartDay());
		
		// June
		date = new Date(2012, 5, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(4, model.getMonthStartDay());
		
		// July
		date = new Date(2012, 6, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(6, model.getMonthStartDay());
		
		// August
		date = new Date(2012, 7, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(2, model.getMonthStartDay());
		
		// September
		date = new Date(2012, 8, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(5, model.getMonthStartDay());
		
		// October
		date = new Date(2012, 9, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(0, model.getMonthStartDay());
		
		// November
		date = new Date(2012, 10, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(3, model.getMonthStartDay());
		
		// December
		date = new Date(2012, 11, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(5, model.getMonthStartDay());
		
		// Feburar not leap year
		date = new Date(2011, 1, 1, 12, 00);
		model = new DateModel(date);
		assertEquals(1, model.getMonthStartDay());	
	}
	
	
	public void testDaysInMonth() {
		
		// Januar
		Date date = new Date(2012, 0, 1, 12, 00);
		DateModel model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// Februar leapyear
		date = new Date(2012, 1, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 29);
		
		// March
		date = new Date(2012, 2, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// April
		date = new Date(2012, 3, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 30);
		
		// May
		date = new Date(2012, 4, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// June
		date = new Date(2012, 5, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 30);
		
		// July
		date = new Date(2012, 6, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// August
		date = new Date(2012, 7, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// September
		date = new Date(2012, 8, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 30);
		
		// October
		date = new Date(2012, 9, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// November
		date = new Date(2012, 10, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 30);
		
		// December
		date = new Date(2012, 11, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 31);
		
		// Feburar not leap year
		date = new Date(2011, 1, 1, 12, 00);
		model = new DateModel(date);
		
		assertEquals(model.getDaysInMonth(), 28);
		
		
		
	}
	
}
