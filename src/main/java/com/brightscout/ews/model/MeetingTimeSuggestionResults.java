package com.brightscout.ews.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingTimeSuggestionResults {

	@NonNull
	private List<MeetingTimeSuggestion> meetingTimeSuggestions;
}
