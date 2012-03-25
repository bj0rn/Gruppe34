package no.ntnu.fp.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import no.ntnu.fp.model.Notification;

public class NotificationListModel extends AbstractListModel {
	
	private List<Notification> notifications;
	
	public NotificationListModel() {
		notifications = new ArrayList<Notification>();
	}
	
	public NotificationListModel(List<Notification> list) {
		
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
	
}
