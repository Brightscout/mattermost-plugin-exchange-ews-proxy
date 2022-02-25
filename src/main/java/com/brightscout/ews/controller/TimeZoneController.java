package com.brightscout.ews.controller;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.TimeZoneService;

@RestController
@RequestMapping("/api/timezone")
@Validated
public class TimeZoneController {

	private EwsService ewsService;

	private TimeZoneService timeZoneService;

	@Autowired
	public TimeZoneController(EwsService ewsService, TimeZoneService timeZoneService) {
		this.ewsService = ewsService;
		this.timeZoneService = timeZoneService;
	}

	@GetMapping
	public ResponseEntity<String> getTimeZone(@RequestParam @Email String email) throws InternalServerException {
		return timeZoneService.getTimeZone(ewsService.impersonateUser(email), email);
	}
}
