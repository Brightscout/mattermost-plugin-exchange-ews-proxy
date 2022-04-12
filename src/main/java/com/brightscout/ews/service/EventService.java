package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface EventService {

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<List<Event>> getEvents(ExchangeService service, String start, String end) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Event> getEventById(ExchangeService service, String id) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Event> acceptEvent(ExchangeService service, String eventId) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Event> declineEvent(ExchangeService service, String eventId) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Event> tentativelyAcceptEvent(ExchangeService service, String eventId) throws InternalServerException;
}
