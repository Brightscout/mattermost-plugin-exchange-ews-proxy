package com.ews.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EventResponseStatus {

	private String response; // ResponseType

	private String time; // LastResponseTime

	public EventResponseStatus() {
	}

	public EventResponseStatus(@NonNull String response) {
		this.response = response;
	}

	public EventResponseStatus(@NonNull String response, @NonNull String time) {
		this.response = response;
		this.time = time;
	}
}
