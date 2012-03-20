package no.ntnu.fp.model;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class XmlHandler {
	public static void main(String[] args) {
		String test = loginToXml("bj¿rn", "123");
		String res[] = loginFromXml(test);
		System.out.println("Username " +res[0] );
		System.out.println("Password "+res[1]);
		
		System.out.println(inspectMethod(test));
		
		System.out.println(loginSuccessful());
		System.out.println(loginUnsucessful());
		
		System.out.println(inspectStatus(loginSuccessful()));
	}
	
	//Fields
	
	
	//Constructor
	
	//Methods
	
	public static String loginToXml(String username, String password){
		Element element = new Element("request");
		Element header = new Element("header");
		
		Element method = new Element("method");
		method.appendChild("login");
		Element auth = new Element("authenticate");
		Element user = new Element("user");
		user.appendChild(username);
		Element pass = new Element("pass");
		auth.appendChild(user);
		auth.appendChild(pass);
		pass.appendChild(password);
		
		header.appendChild(method);
		header.appendChild(auth);
		element.appendChild(header);
		System.out.println(element.toXML());
		return element.toXML();
		
	}
	
	
	public static String loginSuccessful(){
		Element root = new Element("request");
		Element header = new Element("header");
		Element method = new Element("method");
		method.appendChild("login");
		Element status = new Element("status");
		status.appendChild("200");
		Element message = new Element("message");
		message.appendChild("authenticated");
		
		header.appendChild(method);
		header.appendChild(status);
		header.appendChild(message);
		root.appendChild(header);
		return root.toXML();
	}
	
	
	public static String loginUnsucessful(){
		Element root = new Element("request");
		Element header = new Element("header");
		Element method = new Element("method");
		method.appendChild("login");
		Element status = new Element("status");
		status.appendChild("401");
		Element message = new Element("message");
		message.appendChild("not authanticated");
		
		header.appendChild(method);
		header.appendChild(status);
		header.appendChild(message);
		root.appendChild(header);
		return root.toXML();
	}
	
	public static String [] loginFromXml(String xml){
		String res[]= new String[]{"", ""};
		
		Pattern p = Pattern.compile("<(user|pass)>([^<]+)</");
		Matcher m = p.matcher(xml);
		int i = 0;
		while (m.find()) {
			//System.out.println(i + ": " + m.group(1) + "=" + m.group(2));
			res[i] = m.group(2);
			i++;
		}
		
		return res;
		
	}
	
	
	public static String inspectMethod(String xml){
		String res = null;
		Pattern p = Pattern.compile("<method>([^<]+)</");
		Matcher m = p.matcher(xml);
		while(m.find()){
			res = m.group(1);
		}
		
		return res;
	}
	
	
	public static String inspectStatus(String xml){
		String res = null;
		Pattern p = Pattern.compile("<status>([^<]+)</");
		Matcher m = p.matcher(xml);
		while(m.find()){
			res = m.group(1);
		}
		return res;
	}
	
	public String meetingToXml(CalendarEntry calendarEntry){
		
		
		return "";
	}
}
