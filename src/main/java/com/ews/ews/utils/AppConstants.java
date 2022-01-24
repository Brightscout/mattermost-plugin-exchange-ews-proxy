package com.ews.ews.utils;

import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;

public class AppConstants {

	public static final int MAX_NUMBER_OF_EVENTS = 20;

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static final Map<String, MeetingAttendeeType> MEETING_ATTENDEE_TYPE_MAP = new HashMap<>() {
		{
			put("organizer", MeetingAttendeeType.Organizer);
			put("required", MeetingAttendeeType.Required);
			put("optional", MeetingAttendeeType.Optional);
		}
	};

}
