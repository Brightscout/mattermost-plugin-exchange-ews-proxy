package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Attendee {

	@NonNull
	private String type;

	@NonNull
	private EventResponseStatus status;

	@NonNull
	private EmailAddress emailAddress;

	public Attendee(@NonNull EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
}
