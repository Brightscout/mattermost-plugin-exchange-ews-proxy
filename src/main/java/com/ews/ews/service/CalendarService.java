package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.Calendar;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface CalendarService {
	
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws Exception;
	
	public ResponseEntity<Calendar> getCalendar(ExchangeService service) throws Exception;
}
