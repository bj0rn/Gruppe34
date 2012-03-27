package no.ntnu.fp.model;

import junit.framework.ComparisonFailure;

public interface ContainComparable<T> extends Comparable<T> {

	public boolean contains(T o);

	public int compareTo(Duration o);
	
	
}
