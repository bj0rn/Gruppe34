package no.ntnu.fp.gui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class JEditableList extends JPanel implements ListDataListener {

	private ListCellRenderer listCellRenderer;
	private ListModel dataModel;
	
	public JEditableList(ListModel model, ListCellRenderer listCellRenderer) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setCellRenderer(listCellRenderer);
		setListModel(model);
	}

	private void setListModel(ListModel model) {
		if (model != null) {
			if (dataModel != null) {
				dataModel.removeListDataListener(this);
			}
			
			dataModel = model;
			dataModel.addListDataListener(this);
			update();
		}
	}

	private void update() {
		int i=0;
		int n=getComponents().length;
		
		for (int j=0; j<dataModel.getSize(); j++) {
			
			Component comp = listCellRenderer.getListCellRendererComponent(null, null, j, false, false);
			if (i <  n) {
				remove(i);
				add(comp, i);
			} else {
				add(comp);
			}
		}
		
		for (;i<n; i++) {
			remove(i);
		}
	}
	
	public void setCellRenderer(ListCellRenderer listCellRenderer) {
		this.listCellRenderer = listCellRenderer;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		update();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		update();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		update();
	}
	
}
