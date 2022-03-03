package com.brightscout.ews.model.event;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Event {

	private String id;

	private String calUId;

	@NotBlank
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

	private boolean isAttendeeOrganizer;

	public Event() {
	}

	public Event(@NonNull String id) {
		this.id = id;
	}
}
