package com.ews.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Attendee {

	private String type;

	private EventResponseStatus status;

	private EmailAddress emailAddress;

	public Attendee() {
	}

	public Attendee(@NonNull EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Attendee(@NonNull String type, @NonNull EventResponseStatus status, @NonNull EmailAddress emailAddress) {
		this.type = type;
		this.status = status;
		this.emailAddress = emailAddress;
	}
}
