package com.brightscout.ews.model;

import java.util.List;

import com.brightscout.ews.model.event.Event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CalendarViewSingleResponse {

	@NonNull
	private String id;

	@NonNull
	private List<Event> events;
}
