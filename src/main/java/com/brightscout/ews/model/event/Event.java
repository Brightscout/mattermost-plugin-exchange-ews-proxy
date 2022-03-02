package com.brightscout.ews.model.event;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Event {

	@NonNull
	private String id;

	private String calUId;

	@NotBlank
	private String subject;

	private ItemBody body;

	private String importance;

	private boolean isAllDay; // IsAllDayEvent

	private boolean isCancelled;

	private boolean responseRequested; // IsResponseRequested

	private String showAs;

	private DateTime start;

	private DateTime end;

	private int reminderMinutesBeforeStart;

	private String location;

	private EventResponseStatus responseStatus;

	private List<Attendee> attendees;

	private Attendee organizer;

	private String timezone;

	private String webLink;

	private boolean isAttendeeOrganizer;
}
