package com.brightscout.ews.model;

import com.brightscout.ews.model.event.DateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingTimeSuggestion {

	@NonNull
	private DateTime meetingTimeSlot;
}
