package com.ews.ews.model;

import java.util.ArrayList;

import com.ews.ews.model.event.Event;

public class CalendarViewSingleResponse {
	
	private String id;
	
	private ArrayList<Event> events;

	public CalendarViewSingleResponse(String id, ArrayList<Event> events) {
		super();
		this.id = id;
		this.events = events;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

}
