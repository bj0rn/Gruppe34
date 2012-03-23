package no.ntnu.fp.util;


import java.awt.datatransfer.DataFlavor;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeLord {
	//sql format:  2012-03-22 14:04:35 
	//java format: Thu Mar 22 14:06:07 CET 2012
	public static String changeDateToSQL(Date date) {
		return (date.getYear()+1900) +"-"+
			(((date.getMonth()+1)<10)? "0" : "")
			+ (date.getMonth()+1)+"-"
			+date.getDate()+" "+date.getHours()+":"
			+date.getMinutes()+":"+date.getSeconds();
	}
	
	public static Date changeDateToJava(String s) 
			throws IndexOutOfBoundsException {		
			Date d = new Date(
				Integer.parseInt(s.substring(0,4)),
				Integer.parseInt(s.substring(5,7)),
				Integer.parseInt(s.substring(8,10)),
				Integer.parseInt(s.substring(11,13)),
				Integer.parseInt(s.substring(14,16)),
				Integer.parseInt(s.substring(17,19))
				);
		return d;
	}
	
	public static String formatDate(Date date) {
		DateFormat f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRENCH);
		return f.format(date);
	}

}
