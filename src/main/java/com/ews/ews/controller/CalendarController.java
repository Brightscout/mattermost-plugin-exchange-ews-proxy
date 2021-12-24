package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.Calendar;
import com.ews.ews.service.CalendarService;
import com.ews.ews.service.EWSService;

@RestController
@RequestMapping("/api/calendar/")
public class CalendarController {
	
	@Autowired
	EWSService ewsService;
	
	@Autowired
	CalendarService calendarService;
	
	
	@PostMapping
	public ResponseEntity<Calendar> createCalendar(@RequestParam String email, @RequestBody Calendar calendar) throws Exception {
		return this.calendarService.createCalendar(ewsService.impersonateUser(email), calendar);
	}
	
	@GetMapping
	public ResponseEntity<Calendar> getCalendar(@RequestParam String email, @RequestParam String id) throws Exception {
		return this.calendarService.getCalendar(ewsService.impersonateUser(email), id);
	}
	
}
