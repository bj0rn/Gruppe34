package no.ntnu.fp.model;

import java.util.Date;
import java.util.SortedSet;

import no.ntnu.fp.util.TimeLord;

public class Duration implements ContainComparable<Duration> {

	private Date from;
	private Date to;
	
	public Duration(Date from, Date to) {
		setFrom(from);
		setTo(to);
	}
	
	public void setFrom(Date from) {
		this.from = from;
	}
	
	public void setTo(Date to) {
		this.to = to;
	}
	
	public Date getFrom() {
		return from;
	}
	
	public Date getTo() {
		return to;
	}

	@Override
	public int compareTo(Duration o) {
		
		int startDiff = from.compareTo(o.from);
		
		if (startDiff == 0) {
			 return to.compareTo(o.to);
		} else {
			return startDiff;
		}
	}

	@Override
	public boolean contains(Duration o) {
		
		//System.out.println(from.compareTo(o.from));
		//System.out.println(to.compareTo(o.to));
		
		boolean before = (to.compareTo(o.from) < 0);
		//boolean after = 
		
		///return ! && !(from.compareTo(o.to) > 0);  
		return false;
		//from.compareTo(o.from) <= 0 && to.compareTo(o.to) >= 0
	}
	
	public String toString() {
		return TimeLord.formatDate(from) + " " + TimeLord.formatDate(to);
	}
	
	public static void main(String[] args) {
		SortedList<Duration> list = new SortedList<Duration>();
		
		Duration d1 = new Duration(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		Duration d2 = new Duration(new Date(112, 2, 1, 13, 0, 0), new Date(112, 2, 1, 14, 0, 0));
		Duration d3 = new Duration(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 12, 30, 0));
		
		System.out.println(d1.contains(d2) == false);
		System.out.println(d1.contains(d3) == true);
		System.out.println(d2.contains(d3) == false);
		
		list.add(d1);
		list.add(d2);
		list.add(d3);
		
		for (Duration d : list) {
			System.out.println(d);
		}
	}
}