package com.ews.ews.model;

import java.util.List;

import com.ews.ews.model.event.Attendee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMeetingTimesParameters {

	private List<Attendee> attendees;
}
