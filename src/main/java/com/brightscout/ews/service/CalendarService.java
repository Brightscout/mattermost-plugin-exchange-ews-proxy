package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.model.Calendar;
import com.brightscout.ews.model.FindMeetingTimesParameters;
import com.brightscout.ews.model.MeetingTimeSuggestionResults;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface CalendarService {

	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws Exception;

	public ResponseEntity<List<Calendar>> getCalendars(ExchangeService service) throws Exception;

	public ResponseEntity<Calendar> deleteCalendar(ExchangeService service, String calendarId) throws Exception;

	public ResponseEntity<Calendar> getCalendar(ExchangeService service, String calendarId) throws Exception;

	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(ExchangeService service, String organizerEmail,
			FindMeetingTimesParameters findMeetingTimes) throws Exception;
}
