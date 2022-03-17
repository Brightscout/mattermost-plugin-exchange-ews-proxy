package com.brightscout.ews.utils;

import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;

public class AppConstants {

	public static final int MAX_NUMBER_OF_EVENTS = 20;

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static final int SUBSCRIPTION_LIFETIME_IN_MINUTES = 30;
	
	public static final int MAX_NUMBER_OF_USERS_PER_EMAIL = 1;

	public static final Map<String, MeetingAttendeeType> MEETING_ATTENDEE_TYPE_MAP = new HashMap<>() {
		private static final long serialVersionUID = -6534140953829903293L;
		{
			put("organizer", MeetingAttendeeType.Organizer);
			put("required", MeetingAttendeeType.Required);
			put("optional", MeetingAttendeeType.Optional);
		}
	};

	public static final String EXCHANGE_SERVER_ADDRESS = "ews/exchange.asmx";

	public static final String EXCHANGE_OUTLOOK_ADDRESS = "owa";

	public static final String EVENT_URL_FORMAT = "%s/%s/?itemid=%s#exvsurl=1&path=/calendar/item";

	public static final String PATH_SYNC = "/sync";

	public static final String PATH_API = "/api/v1";

	public static final String PATH_SUBSCRIPTION = "/subscription";

	public static final long WAIT_INTERVAL_IN_MILLISECONDS = 3000; // 3 seconds
}
