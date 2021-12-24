package com.ews.ews.model;

import com.ews.ews.model.event.Event;

public class Calendar {
	
	private String id;
	
	private String name;
	
	private Event[] events;
	
	private Event[] calendarView;
	
	private User owner;
	
	public Calendar() {
		
	}
	
	public Calendar(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}

	public Event[] getCalendarView() {
		return calendarView;
	}

	public void setCalendarView(Event[] calendarView) {
		this.calendarView = calendarView;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
	
	
	
}
