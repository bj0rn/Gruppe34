package no.ntnu.fp.net.network;

import java.io.Serializable;

import no.ntnu.fp.model.Authenticate;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5742879083057675825L;
	private Authenticate auth;
	private Object object;
	private Method method;
	
	public Request(Authenticate auth, Object object){
		this.auth = auth;
		this.object = object;
	}
	
	
	public void setObject(Object object){
		this.object = object;
	}
	
	public void setAuth(Authenticate auth){
		this.auth = auth;
	}
	
	
	public Authenticate getAuth(){
		return auth;
	}
	
	public Object getObject(){
		return object;
	}
	
	public void setMethod(Method method){
		this.method = method;
	}
	public Method getMethod(){
		return method;
	}
	
	public enum Method{
		SAVE_PLACE, SAVE_MEETING, SAVE_APPOINTMENT,
		AUTHENTICATE, GET_FULL_USER, DISPATCH_MEETING_REPLY,
		LOGIN_FAILED, LOGIN_SUCCEDED, GET_USERS, REQUEST_FAILED, 
		GET_USERS_RESPONSE, GET_FULL_USER_RESPONSE, SAVE_MEETING_RESPONSE,
		MEETING_NOTIFICATION, SAVE_APPOINTMENT_RESPONSE, 
		DISPATCH_MEETING_REPLY_RESPONSE, GET_LIST_OF_ROOMS,
		GET_LIST_OF_ROOMS_RESPONSE, 
		CHANGE_MEETING_NOTFICATION, CHANGE_APPOINTMENT_NOTIFICATION, 
		CANCEL_VIEW, CANCEL_VIEW_SUCCEDED, DELETE_MEETING,
		DELETE_APPOINTMENT
	}
}
