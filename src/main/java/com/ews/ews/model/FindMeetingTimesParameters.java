package com.ews.ews.model;

import java.util.List;
import javax.validation.constraints.NotNull;

import com.ews.ews.model.event.Attendee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMeetingTimesParameters {

	@NotNull
	private List<Attendee> attendees;
}
