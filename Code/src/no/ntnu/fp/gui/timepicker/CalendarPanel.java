package no.ntnu.fp.gui.timepicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel implements MouseListener, PropertyChangeListener {

	private final static int ROWS = 6;
	private final static int COLS = 7;
	
	public String[] DAY_LABELS = new String[] { "Ma", "Ti", "On", "To", "Fr", "Lø", "Sø" };
	
	private DayPanel[][] grid;
	private DateModel model;
	
	public CalendarPanel() {
		this(new DateModel(new Date()));
	}
	
	public CalendarPanel(DateModel date) {

		setLayout(new GridLayout(7,7));

		model = date;
		model.addPropertyChangeListener(this);
		
		for (String day : DAY_LABELS) {
			JPanel panel = new JPanel();
			JLabel label = new JLabel(day);
			panel.add(label);
			add(panel);
		}
		
		grid = new DayPanel[ROWS][COLS];		
		for (int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				DayPanel panel = new DayPanel(model);
				add(panel);
				grid[i][j] = panel;
			}
		}
			
		update();
	}
	
	private void update() {

		int startday = model.getMonthStartDay();
		int daysInMonth = model.getDaysInMonth();
		int activeday = model.getDay();
		
		int day = -1;
		
		for(int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				
				grid[i][j].clearLabel();
				grid[i][j].setActive(false);

				if (i==0 && j == startday) {
					day = 1;
				}
				
				if (day > 0 && day <= daysInMonth) {
					grid[i][j].setDay(day);
					day++;
				}
				
				if (day == activeday) {
					grid[i][j].setActive(true);
				}
				
				grid[i][j].update();
			}
		}
	}
	
	public void setModel(Date date) {
		model.setDate(date);
	}
	
	public DateModel getModel() {
		return model;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		JLabel label = null;
		if (source instanceof JPanel) {
			JPanel panel = (JPanel)source;
			label = (JLabel)panel.getComponent(0);
			
		} else { // (source instanceof JLabel)
			label = (JLabel) source;
		}
		try {
			int day = Integer.parseInt(label.getText());
			model.setDay(day);
			
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		update();
	}
	
}
