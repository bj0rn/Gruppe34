package no.ntnu.fp.net.network;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import no.ntnu.fp.model.Appointment;
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
		communicationController.authenticate("bjorn", "lol");
		User user = new User("bjorn", "lol");
		User user2 = new User("havard", "test");
		Meeting meeting = new Meeting("test");
		meeting.setOwner(user);
		meeting.addParticipant(user2, State.Pending);
		
		System.out.println(communicationController.saveMeeting(meeting));
//		
		//System.out.println(communicationController.dispatchMeetingReply(user, meeting, state));
		
		
		//User user = communicationController.getFullUser("havard", "test", "havard");
		//System.out.println("Got user: "+user.getName());
//		List <User> users = communicationController.getUsers("havard", "test");
//		for(User u: users){
//			System.out.println(u.getName());
//			System.out.println(u.getEmail());
//		}
		//communicationController.getUsers("bjorn", "lol");
		System.out.println("Data sent");
		
	}
}
