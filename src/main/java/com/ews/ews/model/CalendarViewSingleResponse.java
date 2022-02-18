package com.ews.ews.model;

import java.util.List;

import com.ews.ews.model.event.Event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewSingleResponse {

	private String id;

	private List<Event> events;

	public CalendarViewSingleResponse(@NonNull String id, @NonNull List<Event> events) {
		this.id = id;
		this.events = events;
	}
}
