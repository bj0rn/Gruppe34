package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SortedList<T extends ContainComparable<T>> implements Iterable<T> {
	
	private List<T> list = new ArrayList<T>();
	
	public SortedList() {
		
	}
	
	public SortedList(List<T> list) {
		this.list = list;
		Collections.sort(list);
	}
	
	public void add(T elem) {
		list.add(elem);
		Collections.sort(list);
	}
	
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}
}