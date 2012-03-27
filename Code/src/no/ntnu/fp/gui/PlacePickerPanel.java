package no.ntnu.fp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Location;
import no.ntnu.fp.model.Place;
import no.ntnu.fp.model.Room;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.util.GridBagHelper;



public class PlacePickerPanel extends JPanel implements PropertyChangeListener {
	private List<Room> locs;
	private JLabel desc, cap, roomName, misc;
	private JTextField descComp, capComp, nameComp;
	private Location selectedLoc;
	private JList locList;
	private JCheckBox cbRooms, cbPlaces;
	private CommunicationController cCtrl;
	private DefaultListModel listModel;
	private CalendarEntry model;
	protected PropertyChangeSupport pcs;
	boolean showRooms = true, showPlaces = true;
	
	public final static transient String LOCATIONC_PROPERTY = "Location Change";
	
	protected GridBagLayout grid;
	protected GridBagConstraints constraints;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == CalendarEntry.LOC_PROPERTY) {
			Location l = model.getLocation();
			boolean b = (l instanceof Room);
			nameComp.setText((b ? ((Room) l).getName() : "N/A"));
			capComp.setText((b ? ((Room) l).getCapacity()+"" : "N/A"));
			descComp.setText(l.getDescription());
			}
		if (evt.getPropertyName() == CalendarEntry.START_PROPERTY
				|| evt.getPropertyName() == CalendarEntry.END_PROPERTY) {
			Date e = model.getEndDate();
			Date s = model.getStartDate();
			if (e != null && s != null) {
				drawListOfLocations();
			}
			
		}
			
	}//end propertyChange

	public PlacePickerPanel(CalendarEntry model) {
		this();
		this.setModel(model);
	}
	/**
	 * 
	 * @param ce
	 * 		The CalendarEntry for which to select a location
	 */
	public PlacePickerPanel() {
		this.cCtrl = CommunicationController.getInstance();
		this.locs = cCtrl.getListOfRooms();
		
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
		this.listModel = new DefaultListModel();
		
		this.locList = new JList();
			this.locList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.locList.setModel(listModel);
		//this.locs = new ArrayList<Location>();
		
		
		
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
		
		
		//Begin listeners
		locList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Location newLoc = (Location) locList.getSelectedValue();
					if (newLoc != null)
						model.setLocation(newLoc);
				}
			}
		});
		cbPlaces.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPlaces = !showPlaces;
				updatePanel();
			}
		});
		cbRooms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRooms = !showRooms;
				updatePanel();
			}
		});
		//TEST CODE
//		this.locs = new ArrayList<Location>();
//		Location loc1 = new Room(23, "Room1", "The first room", 44);
//		Location loc2 = new Place(22, "strangewhere");
//		Location loc3 = new Room(34, "SPACE.", "Spaaaaaace", Integer.MAX_VALUE);
//		locs.add(loc1);
//		locs.add(loc2);
//		locs.add(loc3);
		
		
		updatePanel();
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
		drawListOfLocations();
	}
	public void setLocation(Location loc) {
		this.selectedLoc = loc;
		updatePanel();
	}
	public void setListOfRooms(List<Room> list) {
		this.locs = list;
		updatePanel();
	}
	/**
	 * redraws the current available locations.
	 */
	private void drawListOfLocations() {
		if (listModel == null)
			listModel = new DefaultListModel();
		
		this.listModel.clear();
		
		if (this.locs == null) {
			listModel.addElement("Nothing's available");
			locList.setEnabled(false);
			return;
		}
		
		locList.setEnabled(true);
		if (this.model == null)
			return;
		
		for (Location l : locs) {
			if (((l instanceof Room) && showRooms && ((Room) l).isAvailable(model.getStartDate(), model.getEndDate()) 
					|| ((l instanceof Place) && showPlaces))) {
				this.listModel.addElement(l);
			}
		}
		if (listModel.isEmpty()) {
			listModel.addElement("Nothing's available");
			locList.setEnabled(false);
		}
	}
	
	private ArrayList<Room> getAvailableRooms() {
		
		return null;
	}
	
	private Location getSelectedLocation() {
		return this.selectedLoc;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		pcs.addPropertyChangeListener(l);
	}
	/**
	 * 
	 * @param ce
	 * 		this is just the model of the panel this 'belongs' to.
	 * 
	 */
	public void setModel(CalendarEntry ce) {
		if (ce != null) {
			if (this.model != null) {
				this.model.removePropertyChangeListener(this);
			}
		this.model = ce;
		this.selectedLoc = model.getLocation();
		model.addPropertyChangeListener(this);
		updatePanel();
		}	
	}
	
	

}
