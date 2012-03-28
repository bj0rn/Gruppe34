package no.ntnu.fp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

	public static final String DEFAULT_IP = "127.0.0.1";
	
	public static final String LOGIN_TITLE = "Login";
	public static final String USERNAME_LABEL = "Brukernavn";
	public static final String PASSWORD_LABEL = "Passord";
	public static final String IP_LABEL = "IP";
	public static final String LOGIN_BUTTON_LABEL = "Logg inn";
	
	private JTextField usernameField; 
	private JPasswordField passwordField;
	private JTextField ipField;
	
	private JButton loginButton;
	
	private Authenticate model;
	private String ip = DEFAULT_IP;
	
	public LoginFrame() {
		
		model = new Authenticate();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(LOGIN_TITLE);
		title.setFont(StylingDefinition.FRAME_TITLE_FONT);
		title.setBorder(new EmptyBorder(5,5,5,5));
		panel.add(title, BorderLayout.NORTH);
		
		JPanel content = new JPanel();
		content.setBorder(new EmptyBorder(2, 2, 2, 2));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JLabel usernameLabel = new JLabel(USERNAME_LABEL);
		content.add(usernameLabel);
		
		LoginButtonAction loginAction = new LoginButtonAction(this, model);
		
		usernameField = new JTextField();
		usernameField.addKeyListener(this);
		usernameField.addActionListener(loginAction);
		content.add(usernameField);
		
		JLabel passwordLabel = new JLabel(PASSWORD_LABEL);
		content.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(this);
		passwordField.addActionListener(loginAction);
		content.add(passwordField);
		
		JLabel ipLabel = new JLabel(IP_LABEL);
		content.add(ipLabel);
		
		ipField = new JTextField();
		ipField.setText(DEFAULT_IP);
		ipField.addKeyListener(this);
		ipField.addActionListener(loginAction);
		content.add(ipField);
		
		panel.add(content, BorderLayout.CENTER);
		
		loginButton = new JButton(LOGIN_BUTTON_LABEL);
		loginButton.addActionListener(loginAction);
		panel.add(loginButton, BorderLayout.SOUTH);
		
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(200,200));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public String getIP() {
		return ip;
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
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == usernameField) {
			model.setUsername(usernameField.getText());
		} else  if (e.getSource() == passwordField) {
			model.setPassword(new String(passwordField.getPassword()));
		} else if (e.getSource() == ipField) {
			ip = ipField.getText();
		}
	}

}
