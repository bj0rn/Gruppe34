package no.ntnu.fp.model;


public interface ModelChangeListener {
	
	public int newModel(String type);
	
	public boolean propertyChange(Object source, String key);
	
	public void distribute(Object source);

}
