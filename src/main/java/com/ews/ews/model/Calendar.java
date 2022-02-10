package com.ews.ews.model;

import com.ews.ews.model.event.Event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
