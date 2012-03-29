package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.model.CalendarEntry;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.util.Log;
import no.ntnu.fp.util.TimeLord;

/**
 * Shows a grid representing 7days (cols) and 24 hour (rows) with CalendarEnties
 * placed on the sheet with x, y and height corresponding to their weekday, startTime and duration
 * 
 * @author andrephilipp
 * 
 */

public class WeekSheet extends JPanel implements PropertyChangeListener{

	final Color GRID_COLOR = Color.LIGHT_GRAY;
	final Color HOURLABELS_COLOR = Color.LIGHT_GRAY;
	private ArrayList<JLabel> hours = new ArrayList<JLabel>();
	
	//public ArrayList<CalendarEntryView> events = new ArrayList<CalendarEntryView>();
	
	private WeekSheetAdapter adapter;

	private int cellHeight;
	private int cellWidth;
	private int hourColWidth;
	private JPanel weekHeader;

	public WeekSheet(WeekSheetAdapter adapter) {
		
		cellHeight = 50;
		cellWidth = 100;
		hourColWidth = 40;
		
		this.adapter = adapter;
		adapter.addPropertyChangeListener(this);
		setBackground(Color.white);
		addEvents();
		setPreferredSize(new Dimension(40+(100*7), (24*50)));
		setLayout(null);
		updateSheet();
	}
	
	private void addEvents() {
		
		for(CalendarEntryView cev: adapter){
			cev.setPosition();
			add(cev);
		}
	}
	
	public void updateSheet() {
		JComponent parent = ((JComponent)getParent());
		
		if (parent != null) {
			Log.out("update");
			removeAll();
			addEvents();
			parent.revalidate();
			parent.repaint();
		}
	}
	
	public int getCellHeight() {
		return cellHeight;
	}
	
	public int getCellWidth() {
		return cellWidth;
	}
	
	public int getHourColWidth() {
		return hourColWidth;
	}
	
	/*private void addHourLabels() {
		JLabel hour;
		for (int i = 0; i < 24; i++) {
			hour = new JLabel(i + ":00");
			hour.setForeground(HOURLABELS_COLOR);
			hour.setBounds(0, i * cellHeight, hour.getPreferredSize().width,
					hour.getPreferredSize().height);
			add(hour);
			hours.add(hour);
			hourColWidth = hour.getWidth() + 2;
		}
	}*/

	public void paint(Graphics g) {

		super.paint(g);
		
		paintGrid(g);
		paintHours(g);
		
		
		
		
	}

	private void paintHours(Graphics g) {
		
		for (int i=0; i<24; i++) {
			g.drawString(TimeLord.formatTime(i), 0, i*cellHeight + 15);
		}
	}

	/*private void paintEvents() {
		int x, y, width, height;
		for (CalendarEntryView e : events) {
			x = hourColWidth + (e.getModel().getDayOfWeek() - 1) * cellWidth;
			y = (e.getModel().getTimeOfDay() * cellHeight) / 60;
			width = cellWidth;
			height = (int) (e.getModel().getDuration() * cellHeight) / 60;
			System.out.println("X: "+x);
			System.out.println("Y: "+y);
			System.out.println("Width: "+width);
			System.out.println("Height: "+height);
			
			e.setBounds(x, y, width, height);
		}
	}*/

	/**
	 * Paints a 24x7 grid representing 24 hours and 7 days
	 * 
	 * @param Graphic
	 *            object to paint to
	 */
	private void paintGrid(Graphics g) {
		g.setColor(Color.WHITE);
		//g.drawRect(0, 0, getWidth(), getHeight());
		g.setColor(GRID_COLOR);
		for (int i = 0; i < 24; i++) {
			int x1 = 0;
			int y1 = i * cellHeight;
			int x2 = getWidth();
			int y2 = i * cellHeight;
			
			g.drawLine(x1, y1, x2, y2);
		}

		for (int i = 0; i < 7; i++) {
			g.drawLine(i * cellWidth + hourColWidth, 0, i * cellWidth + hourColWidth, getHeight());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		updateSheet();
	}
}
