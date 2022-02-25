package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.exception.InternalServerException;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface TimeZoneService {
	
	public ResponseEntity<String> getTimeZone(ExchangeService service, String userEmail) throws InternalServerException; 
}
