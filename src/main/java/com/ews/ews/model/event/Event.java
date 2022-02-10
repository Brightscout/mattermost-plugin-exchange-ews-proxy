package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

	private String id;

	private String iCalUID;

	private String subject;

	// NA: private String bodyPreview;

	private ItemBody body;

	private String importance;

	private boolean isAllDay; // IsAllDayEvent

	private boolean isCancelled;

	// NA: private boolean isOrganizer;

	private boolean responseRequested; // IsResponseRequested

	private String showAs;

	// NA: private String webLink;

	private DateTime start;

	private DateTime end;

	private int reminderMinutesBeforeStart;

	private String location;

	private EventResponseStatus responseStatus;

	private Attendee[] attendees;

	private Attendee organizer;

	private String timezone;

	private String webLink;

	private boolean isOrganizer;

	public Event() {
	}

	public Event(String id) {
		this.id = id;
	}

	public boolean isOrganizer() {
		return isOrganizer;
	}

	public void setIsOrganizer(boolean isOrganizer) {
		this.isOrganizer = isOrganizer;
	}

}
