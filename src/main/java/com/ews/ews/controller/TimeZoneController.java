package com.ews.ews.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.service.EwsService;
import com.ews.ews.service.TimeZoneService;

@RestController
@RequestMapping("/api/timezone")
public class TimeZoneController {

	private transient EwsService ewsService;

	private transient TimeZoneService timeZoneService;

	public TimeZoneController(EwsService ewsService, TimeZoneService timeZoneService) {
		this.ewsService = ewsService;
		this.timeZoneService = timeZoneService;
	}

	@GetMapping
	public ResponseEntity<String> getTimeZone(@RequestParam String email) throws Exception {
		return timeZoneService.getTimeZone(ewsService.impersonateUser(email), email);
	}
}
