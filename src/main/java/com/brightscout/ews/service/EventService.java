package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.event.Event;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface EventService {
	
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws InternalServerException;
	
	public ResponseEntity<List<Event>> getEvents(ExchangeService service, String start, String end) throws InternalServerException;

	public ResponseEntity<Event> getEventById(ExchangeService service, String id) throws InternalServerException;
	
	public ResponseEntity<Event> acceptEvent(ExchangeService service, String eventId) throws InternalServerException;
	
	public ResponseEntity<Event> declineEvent(ExchangeService service, String eventId) throws InternalServerException;
	
	public ResponseEntity<Event> tentativelyAcceptEvent(ExchangeService service, String eventId) throws InternalServerException;
}
