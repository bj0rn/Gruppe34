package no.ntnu.fp.gui.timepicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import no.ntnu.fp.util.StringHelper;
import no.ntnu.fp.util.TimeLord;

public class DateTimePicker extends JPanel implements PropertyChangeListener {
	
	private final static String[] MONTH_NAMES = { "Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desemeber" };

	private JButton prevMonthButton;
	private JLabel monthLabel;
	private JButton nextMonthButton;
	
	private CalendarPanel calendar;
	
	private JTextField hourField;
	private JTextField minuteField;
	
	private JButton button;
	
	private DateModel model;
	
	private JTextField field;
	
	public DateTimePicker(JTextField field) {
		this.field = field;
		
		model = new DateModel();
		model.setHours(12);
		model.setMinutes(00);
		model.setSeconds(00);
		
		setLayout(new BorderLayout());
		
		JPanel top = new JPanel();

		prevMonthButton = new JButton("<");
		prevMonthButton.setPreferredSize(new Dimension(20,20));
		prevMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int month = model.getMonth();
				model.setMonth((month - 1));
			}
		});
		top.add(prevMonthButton);
		
		monthLabel = new JLabel(MONTH_NAMES[model.getMonth()]);
		monthLabel.setPreferredSize(new Dimension(70, 20));
		top.add(monthLabel);
		
		nextMonthButton = new JButton(">");
		nextMonthButton.setPreferredSize(new Dimension(20,20));
		nextMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int month = model.getMonth();
				model.setMonth(month + 1);
			}
		});
		top.add(nextMonthButton);
		
		add(top, BorderLayout.NORTH);
		
		calendar = new CalendarPanel(model);
		add(calendar, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		
		hourField = new JTextField(2);
		hourField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String hour = hourField.getText();
				
				try{ 
					model.setHours(Integer.parseInt(hour));
				} catch (NumberFormatException ex) {}
			}
		
		});
		hourField.requestFocus();
		bottom.add(hourField);
		
		bottom.add(new JLabel(":"));
		
		minuteField = new JTextField(2);
		minuteField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String minute = minuteField.getText();
				
				try{ 
					model.setMinutes(Integer.parseInt(minute));
				} catch (NumberFormatException ex) {}
			}
		
		});
		bottom.add(minuteField);
		
		button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		bottom.add(button);
		
		add(bottom, BorderLayout.SOUTH);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		model.addPropertyChangeListener(this);
		
		update();
	}
	
	private void updatePanel() {
		
		if (model != null) {
			
			int month = model.getMonth();
			monthLabel.setText(MONTH_NAMES[month]);
			
			int hours = model.getHours();
			hourField.setText(StringHelper.nullPaddNumber(hours));
			
			int minutes = model.getMinutes();
			minuteField.setText(StringHelper.nullPaddNumber(minutes));	
		}
	}

	private void updateField() {
		
		if (model != null) {
			field.setText(TimeLord.formatDate(model.getDate()));
		}
	}
	
	public void setModel(DateModel date) {
		if (date != null) {
			if (model != null) {
				model.removePropertyChangeListener(this);
			}
			model = date;
			model.addPropertyChangeListener(this);
			updatePanel();
		}
	}
	
	public DateModel getModel() {
		return model;
	}
	
	public void setDate(Date date) {
		model.setDate(date);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JButton button = new JButton();
		JTextField field = new JTextField(20);
		
		field.addFocusListener(new TimePickableFieldListener(field, frame));

		panel.add(button);
		panel.add(field);
		
		frame.setPreferredSize(new Dimension (400, 400));
		frame.setContentPane(panel);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		update();
		
	}

	private void update() {
		updatePanel();
		updateField();
		
	}
}
