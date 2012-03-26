package no.ntnu.fp.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class JEditableList extends JPanel implements ListDataListener {

	private ListCellRenderer listCellRenderer;
	private ListModel dataModel;
	
	public JEditableList(ListModel model, ListCellRenderer listCellRenderer) {
		setLayout(null);
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

	public void update() {
		
		if (dataModel != null) {
			removeAll();
			
			int n = dataModel.getSize();
			
			int y = 0;
			
			for (int i=0; i<n; i++) {
				Component comp = listCellRenderer.getListCellRendererComponent(null, null, i, false, false);
				
				Dimension size = comp.getPreferredSize();
				int width = size.width;
				int height = size.height;
				
				comp.setBounds(0, y, width, height);
				comp.repaint();
				
				y += height;
				
				add(comp);
			}
			setPreferredSize(new Dimension(280, y));
			if (getParent() != null) 
				((JViewport)getParent()).revalidate();
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
