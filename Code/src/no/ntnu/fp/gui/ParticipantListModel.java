package no.ntnu.fp.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractListModel;

import no.ntnu.fp.model.Meeting;

public class ParticipantListModel extends AbstractListModel implements PropertyChangeListener {
	
	private Meeting model;
	
	public ParticipantListModel(Meeting model) {
		this.model = model;
		model.addPropertyChangeListener(this);
	}

	@Override
	public int getSize() {
		return model.getParticipants().size();
	}

	@Override
	public Object getElementAt(int index) {
		return model.getParticipantsSorted().get(index);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		fireContentsChanged(model, 0, getSize());
	}
}
