package no.ntnu.fp.model;

import java.io.Serializable;

public class Place extends Location implements Serializable {
	
	private static final long serialVersionUID = 1141523656955680552L;
	
	public Place(int id, String description) {
		super(id, description);
	}
	
	public String toString() {
		return "Sted: " + description;
	}
}