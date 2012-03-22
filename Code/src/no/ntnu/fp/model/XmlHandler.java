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
		String test = XmlHandler.getFullUserToXMl("test", "password", "havard", "getFullUsers");
		System.out.println(test);
		String res = new XmlHandler().inspectKey(test);
		System.out.println(res);
		
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
	//Just some hack : need to reqwrite this
	public static String getFullUserToXMl(String myUsername, String myPassword, String key, String func){
		Element request = new Element("request");
		Element header = new Element("header");
		Element method = new Element("method");
		method.appendChild(func);
		Element auth = new Element("Authenticate");
		Element user = new Element("user");
		user.appendChild(myUsername);
		Element pass = new Element("pass");
		pass.appendChild(myPassword);
		auth.appendChild(user);
		auth.appendChild(pass);
		
		header.appendChild(method);
		header.appendChild(auth);
		//Data field
		Element data = new Element("data");
		Element keyField = new Element("key");
		keyField.appendChild(key);
		data.appendChild(keyField);
		
		request.appendChild(header);
		request.appendChild(data);
		
		
		return request.toXML();
	}
	
	
	
	
	public static String generateRequest(String username, String password, String func){
		Element element = new Element("request");
		Element header = new Element("header");
		Element method = new Element("method");
		method.appendChild(func);
		Element auth = new Element("authenticate");
		Element user = new Element("user");
		user.appendChild(username);
		Element pass = new Element("pass");
		pass.appendChild(password);
		
		
		auth.appendChild(user);
		auth.appendChild(pass);
		
		
		header.appendChild(method);
		header.appendChild(auth);
		element.appendChild(header);
		//System.out.println(element.toXML());
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
	
	public static String inspectKey(String xml){
		String res = null;
		Pattern p = Pattern.compile("<key>([^<]+)</");
		Matcher m = p.matcher(xml);
		while(m.find()){
			res = m.group(1);
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



	public static String formatResponseXml(String xml, String m) throws ValidityException, ParsingException, IOException{
		Element root = new Element("request");
		Element header = new Element("header");
		Element method = new Element("method");
		method.appendChild(m);
		Element status = new Element("status");
		status.appendChild("200");
		header.appendChild(method);
		header.appendChild(status);
		
		root.appendChild(header);
		
		Builder builder = new Builder();
		Document doc = builder.build(xml, null);
		Element xmlData = doc.getRootElement();
		
		Element data = new Element("data");
		data.appendChild(xmlData);
		root.appendChild(data);

	
	
		return root.toXML();
}
}
