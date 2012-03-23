package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import no.ntnu.fp.model.Authenticate;

public class LoginFrame extends JFrame implements PropertyChangeListener, KeyListener{

	public static final String LOGIN_TITLE = "Login";
	public static final String USERNAME_LABEL = "Brukernavn";
	public static final String PASSWORD_LABEL = "Passord";
	public static final String LOGIN_BUTTON_LABEL = "Logg inn";
	
	private JTextField usernameField; 
	private JPasswordField passwordField;
	
	private JButton loginButton;
	
	private Authenticate model;
	
	public LoginFrame() {
		
		model = new Authenticate();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(LOGIN_TITLE);
		title.setFont(StylingDefinition.FRAME_TITLE_FONT);
		title.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(title, BorderLayout.NORTH);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JLabel usernameLabel = new JLabel(USERNAME_LABEL);
		content.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.addKeyListener(this);
		content.add(usernameField);
		
		JLabel passwordLabel = new JLabel(PASSWORD_LABEL);
		content.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(this);
		content.add(passwordField);
		
		panel.add(content, BorderLayout.CENTER);
		
		loginButton = new JButton(LOGIN_BUTTON_LABEL);
		loginButton.addActionListener(new LoginButtonAction(model));
		panel.add(loginButton, BorderLayout.SOUTH);
		
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName() == Authenticate.PASS_PROPERTY) {
			passwordField.setText(model.getPassword());
		} else if (evt.getPropertyName() == Authenticate.USER_PROPERTY) {
			usernameField.setText(model.getUsername());
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == usernameField) {
			model.setUsername(usernameField.getText());
		} else  if (e.getSource() == passwordField) {
			model.setPassword(passwordField.getText());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
