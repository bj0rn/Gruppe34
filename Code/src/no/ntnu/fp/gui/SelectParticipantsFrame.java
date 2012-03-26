package no.ntnu.fp.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.User;


public class SelectParticipantsFrame implements ListCellRenderer {
	
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JPanel panel = new JPanel();
		
		User user = (User)list.getModel().getElementAt(index);
		
		panel.add(new JLabel(user.getName()));
		panel.add(new JCheckBox("", isSelected));
		
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new ListRenderingFrame();
		frame.show();
	}
}

class ListRenderingFrame extends JFrame implements ListSelectionListener {
	
	
	
	public ListRenderingFrame() {
		
		JLabel labelUsers = new JLabel("Brukere");
		
		JButton saveButton = new JButton(new saveAction("Lagre"));
		JButton cancelButton = new JButton(new cancelAction("Avbryt"));
	
		JPanel participantButtons = new JPanel();
		participantButtons.add(saveButton);
		participantButtons.add(cancelButton);
		
		setTitle("Oversikt over brukere");
		setSize(400,300);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		Vector users = new Vector();
		
		Meeting m = new Meeting(1);
		//Set<User> s = m.getParticipants();
		users.add(new User(getName()));
		
		
		User user = new User("havard");
		user.setName("Håvard Wormdal Høiby");
		User p1 = new User("bjorn");
		p1.setName("Bjørn Åge Tungesvik");
		User p2 = new User("odd");
		p2.setName("Odd Magnus Trondrud");
		User p3 = new User("andy");
		p3.setName("Andre Philipp");
		User p4 = new User("eivind");
		p4.setName("Eivind Kvissel");
		User p5 = new User("tina");
		p5.setName("Tina Syversen");
		
		users.add(user);
		users.add(p1);
		users.add(p2);
		users.add(p3);
		users.add(p4);
		users.add(p5);
		JList list = new JList(users);
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new SelectParticipantsFrame());
		JScrollPane scrollPane = new JScrollPane(list);
		
		JPanel p = new JPanel();
		p.add(scrollPane);
		list.addListSelectionListener(this);
		
		getContentPane().add(labelUsers, "North");
		getContentPane().add(p, "Center");
		getContentPane().add(participantButtons, "South");
	
	}
	//Action for lagring av skjema
	private class saveAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public saveAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("lagre");
        }
    }
    
    //Action for � avbryte skjema
    private class cancelAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public cancelAction(String text) {
        	super(text, null);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	System.out.println("Avbryt");
        }
    }

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}