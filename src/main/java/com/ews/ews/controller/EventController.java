package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.event.Event;
import com.ews.ews.service.EWSService;
import com.ews.ews.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	@Autowired
	EWSService ewsService;
	
	@Autowired
	EventService eventService;
	
	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestParam String email, @RequestBody Event event) throws Exception {
		return this.eventService.createEvent(ewsService.impersonateUser(email), event);
	}
	
}
