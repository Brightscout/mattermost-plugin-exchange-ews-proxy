package com.ews.ews.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.Calendar;
import com.ews.ews.model.FindMeetingTimesParameters;
import com.ews.ews.model.MeetingTimeSuggestionResults;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface CalendarService {
	
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws Exception;
	
	public ResponseEntity<ArrayList<Calendar>> getCalendars(ExchangeService service) throws Exception;

	public ResponseEntity<Calendar> deleteCalendar(ExchangeService service, String calendarId) throws Exception;

	public ResponseEntity<Calendar> getCalendar(ExchangeService service, String calendarId) throws Exception;

	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(ExchangeService service, String organizerEmail, FindMeetingTimesParameters findMeetingTimes) throws Exception;
}
