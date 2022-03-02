package com.brightscout.ews.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.brightscout.ews.model.event.Event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Calendar {

	@NonNull
	private String id;

	@NotBlank
	@NonNull
	private String name;

	private List<Event> events;

	private List<Event> calendarView;

	private User owner;
}
