package no.ntnu.fp.gui.timepicker;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DayPanel extends JPanel implements MouseListener, PropertyChangeListener {

	private JLabel label;
	
	public static final Color BACKGROUND_WEEKDAY = Color.WHITE;
	public static final Color BACKGROUND_SUNDAY = Color.LIGHT_GRAY;
	public static final Color BACKGROUND_ACTIVE = Color.DARK_GRAY;
	public static final Color BACKGROUND = new Color(237,237,237);
	
	public static final Color BORDER_ACTIVE = new Color(100,100,100);
	
	public static final Color LABEL_WEEKDAY = Color.BLACK;
	public static final Color LABEL_SUNDAY = Color.RED;
	public static final Color LABEL_ACTIVE = Color.BLACK;
	
	public static final int BORDER_SIZE = 1;
	
	
	private boolean active = false;
	private DateModel model;
	
	private int day = -1;
	
	public DayPanel(DateModel model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		
		label = new JLabel();
		label.addMouseListener(this);
		add(label);
		
		addMouseListener(this);
	}
	
	public void setDay(int day) {
		this.day = day;
		update();
	}
	
	public void clearLabel() {
		day = -1;
	}
	
	public void setActive(boolean b) {
		active = b;
		update();
	}
	
	public void update() {
		if (day == -1) {
			label.setText("");
		} else {
			label.setText(""+day);
		}
		
		if(active) {
			
			setBorder(BorderFactory.createLineBorder(BORDER_ACTIVE, BORDER_SIZE));
		} else {
			setBorder(BorderFactory.createLineBorder(BACKGROUND, BORDER_SIZE));
			if (isSunday()) {
				setBackground(BACKGROUND_SUNDAY);
				label.setForeground(LABEL_SUNDAY);
			} else if (day != -1) {
				setBackground(BACKGROUND_WEEKDAY);
				label.setForeground(LABEL_WEEKDAY);
			} else {
				setBackground(BACKGROUND);
			}
		}
		
	}

	private boolean isSunday() {
		
		if (day != -1) {
			Calendar c = Calendar.getInstance();
			
			c.set(Calendar.YEAR, model.getYear());
			c.set(Calendar.MONTH, model.getMonth());
			c.set(Calendar.DATE, day);
			
			return c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
		} else {
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (day != -1) {
			model.setDay(day);
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == DateModel.DAY_PROPERTY) {
			if ((Integer)evt.getNewValue() == day) {
				setActive(true);
				update();
			} else {
				setActive(false);
				update();
			}
		}
	}
	
	
}
