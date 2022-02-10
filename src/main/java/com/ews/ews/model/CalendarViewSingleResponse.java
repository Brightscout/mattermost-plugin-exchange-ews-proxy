package com.ews.ews.model;

import java.util.ArrayList;

import com.ews.ews.model.event.Event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewSingleResponse {

	private String id;

	private ArrayList<Event> events;

	public CalendarViewSingleResponse(String id, ArrayList<Event> events) {
		this.id = id;
		this.events = events;
	}

}
