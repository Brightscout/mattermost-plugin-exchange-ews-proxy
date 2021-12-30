package com.ews.ews.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.Calendar;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface CalendarService {
	
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws Exception;
	
	public ResponseEntity<ArrayList<Calendar>> getCalendars(ExchangeService service) throws Exception;
}
