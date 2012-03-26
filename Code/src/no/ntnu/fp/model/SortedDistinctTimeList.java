package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SortedDistinctTimeList<T extends ContainComparable<T>> implements Iterable<T> {
	
	private List<T> list;
	
	public SortedDistinctTimeList() {
		list = new ArrayList<T>();
	}
	
	public SortedDistinctTimeList(List<T> list) {
		this.list = list;
		Collections.sort(list);
	}
	
	public void add(T elem) {
		if (!overlaps(elem)) {
			list.add(elem);
			Collections.sort(list);
		} else {
			throw new IllegalArgumentException("List already contains " + elem);
		}
	}
	
	public boolean overlaps(T elem) {
		
		for(T e : this) {
			if (e.contains(elem)) {
				return true;
			}
		}
		return false;
	}

	public T get(int index) {
		return list.get(index);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}
}