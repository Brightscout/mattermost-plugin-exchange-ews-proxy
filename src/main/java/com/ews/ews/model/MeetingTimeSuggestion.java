package com.ews.ews.model;

import com.ews.ews.model.event.DateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MeetingTimeSuggestion {

	private DateTime meetingTimeSlot;

	public MeetingTimeSuggestion() {
	}

	public MeetingTimeSuggestion(@NonNull DateTime meetingTimeSlot) {
		this.meetingTimeSlot = meetingTimeSlot;
	}
}
