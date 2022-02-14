package com.ews.ews.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MeetingTimeSuggestionResults {

	private List<MeetingTimeSuggestion> meetingTimeSuggestions;

	public MeetingTimeSuggestionResults() {
	}

	public MeetingTimeSuggestionResults(@NonNull List<MeetingTimeSuggestion> meetingTimeSuggestions) {
		this.meetingTimeSuggestions = meetingTimeSuggestions;
	}
}
