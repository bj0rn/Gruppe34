package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.fp.model.Calendar;

public class CalendarPanel extends JPanel {

	private JButton prevWeekButton;
	private JButton nextWeekButton;
	private JButton prevYearButton;
	private JButton nextYearButton;
	
	private JLabel weekLabel;
	private JLabel yearLabel;
	
	private WeekSheet weekSheet;
	
	private List<Calendar> model;
	
	public CalendarPanel() {
		
		setLayout(new BorderLayout());
		
		// Add Buttons
		JPanel buttons = new JPanel();
		
		prevWeekButton = new JButton("<");
		prevWeekButton.addActionListener(new PrevWeekAction(this));
		buttons.add(prevWeekButton);
		
		weekLabel = new JLabel("Uke 10");
		buttons.add(weekLabel);
		
		nextWeekButton = new JButton(">");
		nextWeekButton.addActionListener(new NextWeekAction(this));
		buttons.add(nextWeekButton);
		
		prevYearButton = new JButton("<");
		prevYearButton.addActionListener(new PrevYearAction(this));
		buttons.add(prevYearButton);
		
		yearLabel = new JLabel("2012");
		buttons.add(yearLabel);
		
		nextYearButton = new JButton(">");
		nextYearButton.addActionListener(new NextYearAction(this));
		buttons.add(nextYearButton);
			
		add(buttons, BorderLayout.NORTH);
		
		weekSheet = new WeekSheet();
		
	}
	
	public List<Calendar> getModel() {
		return model;
	}
	
	public void setModel(List<Calendar> model) {
		this.model = model;
	}

	private void addMockImage() {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/images/calendar.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		JLabel l = new JLabel(new ImageIcon(img));
		add(l, BorderLayout.CENTER);
	}
	
}
