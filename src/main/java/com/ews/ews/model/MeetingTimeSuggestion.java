package com.ews.ews.model;

import com.ews.ews.model.event.DateTime;

public class MeetingTimeSuggestion {

	private DateTime meetingTimeSlot;
	
	public MeetingTimeSuggestion() {
	}

	public MeetingTimeSuggestion(DateTime meetingTimeSlot) {
		this.meetingTimeSlot = meetingTimeSlot;
	}

	public DateTime getMeetingTimeSlot() {
		return meetingTimeSlot;
	}

	public void setMeetingTimeSlot(DateTime meetingTimeSlot) {
		this.meetingTimeSlot = meetingTimeSlot;
	}

}
