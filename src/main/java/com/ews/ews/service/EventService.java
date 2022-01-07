package com.ews.ews.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.event.Event;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface EventService {
	
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws Exception;
	
	public ResponseEntity<ArrayList<Event>> getEvents(ExchangeService service, String start, String end) throws Exception;
	
}
