package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.List;


public class MockListModel {

	private List<MockModel> mockList = new ArrayList<MockModel>();
	
	
	public MockListModel() {
		
	}
	
	public void addMockModel(MockModel model) {
		mockList.add(model);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (MockModel model : mockList) {
			builder.append(model + "\n");
		}
		return builder.toString();
	}
}
