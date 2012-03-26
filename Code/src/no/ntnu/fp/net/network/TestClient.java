package no.ntnu.fp.net.network;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.model.Appointment;
import no.ntnu.fp.model.Authenticate;
import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meeting.State;
import no.ntnu.fp.model.User;
import no.ntnu.fp.net.network.client.Client;
import no.ntnu.fp.net.network.client.ClientWorker;
import no.ntnu.fp.net.network.client.CommunicationController;
import no.ntnu.fp.net.network.client.InternalReceiver;

public class TestClient {
	
	public static void main(String[] args) {
		
		CommunicationController communicationController = CommunicationController.getInstance();
		
		//Spawn threads
		//System.out.println("Send data");
		//communicationController.authenticate("havard", "test");
		System.out.println(communicationController.authenticate(new Authenticate("bjorn", "lol")));
//		List <User> users = communicationController.getUsers();
//		if(users != null){
//			System.out.println("Yeey");
//		}
		
//		User user = communicationController.getFullUser("havard");
//		if(user == null){
//			System.out.println("Failed");
//		}else {
//			System.out.println("User"+user.getName());
//		}
//		
//		Meeting meeting = new Meeting("hallo");
//		Meeting m = new Meeting();
//		User userOwner = new User("bjorn");
//		User p = new User("havard");
//		
//		meeting.addParticipant(p, State.Pending);
//		meeting.setOwner(userOwner);
//		communicationController.saveMeeting(meeting);
		
		
		
		
	}
}
