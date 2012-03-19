package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel {

	private JButton prevWeekButton;
	private JButton nextWeekButton;
	private JButton prevYearButton;
	private JButton nextYearButton;
	
	private JLabel weekLabel;
	private JLabel yearLabel;
	
	public CalendarPanel() {
		
		setLayout(new BorderLayout());
		
		// Add Buttons
		JPanel buttons = new JPanel();
		
		prevWeekButton = new JButton("<");
		//prevWeekButton.addActionListener();
		
		nextWeekButton = new JButton(">");
		
		prevYearButton = new JButton("<");
		nextYearButton = new JButton(">");
		
		weekLabel = new JLabel("Uke 10");
		yearLabel = new JLabel("2012");
		
		buttons.add(prevWeekButton);
		buttons.add(weekLabel);
		buttons.add(nextWeekButton);
		
		buttons.add(prevYearButton);
		buttons.add(yearLabel);
		buttons.add(nextYearButton);
		
		add(buttons, BorderLayout.NORTH);
		
		addMockImage();
		
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
