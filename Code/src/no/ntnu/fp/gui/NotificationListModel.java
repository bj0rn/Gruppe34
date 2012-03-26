package no.ntnu.fp.gui;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import no.ntnu.fp.model.Calendar;
import no.ntnu.fp.model.Notification;

public class NotificationListModel extends AbstractListModel implements PropertyChangeListener {
	
	private Calendar calendar;
	
	private List<Notification> notifications;
	
	public NotificationListModel(Calendar calendar) {
		this.calendar = calendar;
		calendar.addPropertyChangeListener(this);
		this.notifications = calendar.getMeetingNotifications();
	}

	@Override
	public int getSize() {
		return notifications.size();
	}

	@Override
	public Object getElementAt(int index) {
		return notifications.get(index);
	}
	
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
		fireContentsChanged(this, 0, getSize());
	}
	
	public List<Notification> getNotifications() {
		return notifications;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		notifications = calendar.getMeetingNotifications();
		fireContentsChanged(evt.getSource(), 0, getSize());
	}
}
