package no.ntnu.fp.model;


public interface ModelChangeListener {
	
	public int newModel(String type);
	
	public boolean propertyChange(Model source, String key);
	
	public void distribute(Model source);

}
