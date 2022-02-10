package com.ews.ews.model;

import com.ews.ews.model.event.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingTimeSuggestion {

	private DateTime meetingTimeSlot;

	public MeetingTimeSuggestion() {
	}

	public MeetingTimeSuggestion(DateTime meetingTimeSlot) {
		this.meetingTimeSlot = meetingTimeSlot;
	}

}
