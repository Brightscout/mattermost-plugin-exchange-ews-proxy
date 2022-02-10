package com.ews.ews.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingTimeSuggestionResults {

	private ArrayList<MeetingTimeSuggestion> meetingTimeSuggestions;

	public MeetingTimeSuggestionResults() {
	}

	public MeetingTimeSuggestionResults(ArrayList<MeetingTimeSuggestion> meetingTimeSuggestions) {
		this.meetingTimeSuggestions = meetingTimeSuggestions;
	}

}
