package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface TimeZoneService {
	
	public ResponseEntity<String> getTimeZone(ExchangeService service, String userEmail) throws Exception; 
	
}
