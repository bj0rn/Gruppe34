package no.ntnu.fp.model;

import java.io.Serializable;

public class Place extends Location implements Serializable {
	
	private static final long serialVersionUID = 1141523656955680552L;
	
	private String description;
	
	public Place(int id, String description) {
		super(id, description);
	}
}
