package com.ews.ews.model;

import java.util.ArrayList;

import com.ews.ews.model.event.Attendee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMeetingTimesParameters {

	private ArrayList<Attendee> attendees;

}
