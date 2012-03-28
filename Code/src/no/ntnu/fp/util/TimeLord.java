package no.ntnu.fp.util;


import java.awt.datatransfer.DataFlavor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.SpringLayout.Constraints;

public class TimeLord {
	//sql format:  2012-03-22 14:04:35 
	//java format: Thu Mar 22 14:06:07 CET 2012
	public static String changeDateToSQL(Date date) {
		return (date.getYear()+1900) +"-"+
			(((date.getMonth()+1)<10)? "0" : "")
			+ (date.getMonth()+1)+"-"+
			((date.getMonth() < 10) ? "0" : "")
			+ date.getDate()+" "+
			((date.getHours() < 10) ? "0" : "")
			+ date.getHours()+":" +
			((date.getMinutes() < 10) ? "0" : "")
			+date.getMinutes()+":"+
			((date.getSeconds() < 10) ? "0" : "")
			+date.getSeconds();
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
	
	public static Date parseDate(String str) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
		try {
			return formatter.parse(str);
		} catch (ParseException e ) {
			e.printStackTrace();
		}
		
		return new Date();
	}
	
	public static String formatDate(Date date) {
		if (date != null) {
			DateFormat f = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRENCH);
			return f.format(date);
		} else {
			return "";
		}
	}
	
	public static String formatTime(int hour) {
		return (hour <= 9 ? "0" : "") + hour + ":00";  
	}

}
