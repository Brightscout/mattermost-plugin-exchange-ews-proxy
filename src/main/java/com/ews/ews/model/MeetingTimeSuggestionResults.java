package com.ews.ews.model;

public class MeetingTimeSuggestionResults {

	private MeetingTimeSuggestion[] meetingTimeSuggestions;
	
	private String emptySuggestionReason;
	
	public MeetingTimeSuggestionResults() {
	}

	public MeetingTimeSuggestion[] getMeetingTimeSuggestions() {
		return meetingTimeSuggestions;
	}

	public void setMeetingTimeSuggestions(MeetingTimeSuggestion[] meetingTimeSuggestions) {
		this.meetingTimeSuggestions = meetingTimeSuggestions;
	}

	public String getEmptySuggestionReason() {
		return emptySuggestionReason;
	}

	public void setEmptySuggestionReason(String emptySuggestionReason) {
		this.emptySuggestionReason = emptySuggestionReason;
	}

}
