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
public class EventResponseStatus {

	@NonNull
	private String response; // ResponseType

	@NonNull
	private String time; // LastResponseTime

	public EventResponseStatus(@NonNull String response) {
		this.response = response;
	}
}
