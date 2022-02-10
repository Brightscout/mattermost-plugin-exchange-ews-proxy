package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponseStatus {

	private String response; // ResponseType

	private String time; // LastResponseTime

	public EventResponseStatus() {
	}

	public EventResponseStatus(String response) {
		this.response = response;
	}

	public EventResponseStatus(String response, String time) {
		this.response = response;
		this.time = time;
	}

}
