package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.service.EWSService;
import com.ews.ews.service.TimeZoneService;

@RestController
public class TimeZoneController {

	@Autowired
	EWSService ewsService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@GetMapping
	public ResponseEntity<String> getTimeZone(@RequestParam String email) throws Exception {
		return this.timeZoneService.getTimeZone(ewsService.impersonateUser(email), email);
	}
	
}
