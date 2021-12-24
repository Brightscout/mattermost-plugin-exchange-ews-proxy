package com.ews.ews.model.event;

public class EventResponseStatus {
	
	private String response; // ResponseType
	
	private String time; // LastResponseTime

	public EventResponseStatus() {
		
	}
	
	public EventResponseStatus(String response, String time) {
		super();
		this.response = response;
		this.time = time;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
