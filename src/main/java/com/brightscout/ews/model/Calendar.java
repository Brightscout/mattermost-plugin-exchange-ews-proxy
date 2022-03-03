package com.brightscout.ews.model;

import javax.validation.constraints.NotBlank;

import com.brightscout.ews.model.event.Event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Calendar {

	private String id;

	@NotBlank
	private String name;

	private Event[] events;

	private Event[] calendarView;

	private User owner;

	public Calendar() {
	}

	public Calendar(@NonNull String id, @NonNull String name) {
		this.id = id;
		this.name = name;
	}
}
