package com.ews.ews.model.event;

public class Attendee {
	
	private String type;
	
	private EventResponseStatus status;
	
	private EmailAddress emailAddress;

	public Attendee(String type, EventResponseStatus status, EmailAddress emailAddress) {
		this.type = type;
		this.status = status;
		this.emailAddress = emailAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EventResponseStatus getStatus() {
		return status;
	}

	public void setStatus(EventResponseStatus status) {
		this.status = status;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
	
}
