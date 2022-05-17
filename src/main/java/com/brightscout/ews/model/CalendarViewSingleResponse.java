package com.brightscout.ews.model;

import java.util.List;

import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CalendarViewSingleResponse {

	@NonNull
	private String id;

	private List<Event> events;

	@NonNull
	private ApiResponse error;

	public CalendarViewSingleResponse(@NonNull String id, @NonNull List<Event> events) {
		this.id = id;
		this.events = events;
	}
}
