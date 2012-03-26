package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.util.GridBagHelper;



public class PlacePickerPanel extends JPanel implements PropertyChangeListener {
	private ArrayList<Location> locs;
	private JLabel desc, cap, roomName, misc;
	private JTextField descComp, capComp, nameComp;
	private Location selectedLoc;
	private JList locList;
	private JCheckBox cbRooms, cbPlaces;
	private CommunicationController cCtrl;
	private DefaultListModel listModel;
	protected PropertyChangeSupport pcs;
	
	public final static transient String LOCATIONC_PROPERTY = "Location Change";
	
	protected GridBagLayout grid;
	protected GridBagConstraints constraints;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	/**
	 * 
	 * @param ce
	 * 		The CalendarEntry for which to select a location
	 */
	public PlacePickerPanel() {
		//this.cCtrl = CommunicationController.getInstance();
		//this.locs = cCtrl.getListOfRooms();
		this.desc = new JLabel("Rombeskrivelse:");
		this.cap = new JLabel("Romkapasitet:");
		this.misc = new JLabel("Velg et rom");
		this.roomName = new JLabel("Rom:");
		this.cbRooms = new JCheckBox("Rom");
			cbRooms.setSelected(true);
		this.cbPlaces = new JCheckBox("Steder");
			cbPlaces.setSelected(true);
		
		this.descComp = new JTextField(10);
			descComp.setEditable(false);
		this.capComp = new JTextField(10);
			capComp.setEditable(false);
		this.nameComp = new JTextField(10);
			nameComp.setEditable(false);
		
		
			
		this.pcs = new PropertyChangeSupport(this);
			
		this.locList = new JList();
		this.locList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		Location loc1 = new Room(23, "Room1", "The first room", 44);
		Location loc2 = new Place(22, "strangewhere");
		Location loc3 = new Room(34, "SPACE.", "Spaaaaaace", Integer.MAX_VALUE);
		this.listModel = new DefaultListModel();
		listModel.addElement(loc1);
		listModel.addElement(loc2);
		listModel.addElement(loc3);
		this.locList.setModel(listModel);
		
		grid = new GridBagLayout();
		constraints = new GridBagConstraints();
		setLayout(grid);
		
		constraints.gridwidth = constraints.REMAINDER;
		add(misc, GridBagHelper.setConstraints(constraints, 0, 0));
		add(locList, GridBagHelper.setConstraints(constraints, 0, 3));
		constraints.gridwidth = constraints.RELATIVE;
		add(cbRooms, GridBagHelper.setConstraints(constraints, 0, 1));
		add(cbPlaces, GridBagHelper.setConstraints(constraints, 1, 1));
		add(roomName, GridBagHelper.setConstraints(constraints, 0, 4));
		add(nameComp, GridBagHelper.setConstraints(constraints, 1, 4));
		add(cap, GridBagHelper.setConstraints(constraints, 0, 5));
		add(capComp, GridBagHelper.setConstraints(constraints, 1, 5));
		add(desc, GridBagHelper.setConstraints(constraints, 0, 6));
		add(descComp, GridBagHelper.setConstraints(constraints, 1, 6));
		
		updatePanel();
		//Begin listeners
		
		locList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Location oldLoc = selectedLoc;
					setLocation((Location) locList.getSelectedValue());
					System.out.println("YEH");
					//this is probably not a very clever way\
					//of going about doing this kind of thing\
					//then again I've never been much of a wise man.
					pcs.firePropertyChange(LOCATIONC_PROPERTY, oldLoc, selectedLoc);
				}
				
			}
		});
	}
	
	public void updatePanel() {
		if (selectedLoc != null) {
			descComp.setText(selectedLoc.getDescription());
			if (selectedLoc instanceof Room) {
				capComp.setText(((Room) selectedLoc).getCapacity()+"");
				nameComp.setText(((Room) selectedLoc).getName());
			} else {
				capComp.setText("N/A");
				nameComp.setText("N/A");
			}
		}
		
		
	}
	public void setLocation(Location loc) {
		this.selectedLoc = loc;
		updatePanel();
	}
	public void setListOfLocations(List<Location> list) {
		this.listModel.clear();
		for (Location l : list) this.listModel.addElement(l);
	}
	
	private ArrayList<Room> getAvailableRooms() {
		
		return null;
	}
	
	private Location getSelectedLocation() {
		return this.selectedLoc;
	}
	
	
	

}
