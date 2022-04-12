package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.Calendar;
import com.brightscout.ews.model.FindMeetingTimesParameters;
import com.brightscout.ews.model.MeetingTimeSuggestionResults;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface CalendarService {

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<List<Calendar>> getCalendars(ExchangeService service) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Calendar> deleteCalendar(ExchangeService service, String calendarId) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Calendar> getCalendar(ExchangeService service, String calendarId) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(ExchangeService service, String organizerEmail,
			FindMeetingTimesParameters findMeetingTimes) throws InternalServerException;
}
