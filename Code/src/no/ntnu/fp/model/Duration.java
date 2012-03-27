package no.ntnu.fp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;

import no.ntnu.fp.util.TimeLord;

public class Duration implements ContainComparable<Duration>, Serializable {

	private static final long serialVersionUID = -3129242512633880424L;

	private Date from;
	private Date to;
	
	public Duration(Date from, Date to) {
		setFrom(from);
		setTo(to);
		
		if (from.compareTo(to) != -1) {
			throw new IllegalArgumentException("From("+TimeLord.formatDate(from)+") must be before to("+TimeLord.formatDate(to)+")");
		}
	}
	
	private void setFrom(Date from) {
		this.from = from;
	}
	
	private void setTo(Date to) {
		this.to = to;
	}
	
	public final Date getFrom() {
		return from;
	}
	
	public final Date getTo() {
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
		
		boolean isBefore = (to.compareTo(o.from) <= 0);
		boolean isAfter = (from.compareTo(o.to) >= 0);
		
		return !isBefore && !isAfter;
	}
	
	public String toString() {
		return TimeLord.formatDate(from) + " " + TimeLord.formatDate(to);
	}
	
	public static void main(String[] args) {
		SortedDistinctTimeList<Duration> list = new SortedDistinctTimeList<Duration>();
		
		Duration d1 = new Duration(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 13, 0, 0));
		Duration d2 = new Duration(new Date(112, 2, 1, 13, 0, 0), new Date(112, 2, 1, 14, 0, 0));
		Duration d3 = new Duration(new Date(112, 2, 1, 12, 0, 0), new Date(112, 2, 1, 12, 30, 0));
		
		list.add(d1);
		list.add(d2);
		list.add(d3);
		
		for (Duration d : list) {
			System.out.println(d);
		}
	}
}