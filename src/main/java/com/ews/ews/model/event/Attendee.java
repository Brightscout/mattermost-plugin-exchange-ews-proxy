package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attendee {

	private String type;

	private EventResponseStatus status;

	private EmailAddress emailAddress;

	public Attendee() {
	}

	public Attendee(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Attendee(String type, EventResponseStatus status, EmailAddress emailAddress) {
		this.type = type;
		this.status = status;
		this.emailAddress = emailAddress;
	}

}
