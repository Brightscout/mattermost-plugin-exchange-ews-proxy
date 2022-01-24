package com.ews.ews.model;

import java.util.ArrayList;

public class MeetingTimeSuggestionResults {

	private ArrayList<MeetingTimeSuggestion> meetingTimeSuggestions;
	
	public MeetingTimeSuggestionResults() {
	}
	
	public MeetingTimeSuggestionResults(ArrayList<MeetingTimeSuggestion> meetingTimeSuggestions) {
		this.meetingTimeSuggestions = meetingTimeSuggestions;
	}

	public ArrayList<MeetingTimeSuggestion> getMeetingTimeSuggestions() {
		return meetingTimeSuggestions;
	}

	public void setMeetingTimeSuggestions(ArrayList<MeetingTimeSuggestion> meetingTimeSuggestions) {
		this.meetingTimeSuggestions = meetingTimeSuggestions;
	}

}
