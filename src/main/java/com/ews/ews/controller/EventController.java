package com.ews.ews.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.event.Event;
import com.ews.ews.service.EventService;
import com.ews.ews.service.EwsService;
import com.ews.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/event")
public class EventController {

	private transient EwsService ewsService;

	private transient EventService eventService;

	public EventController(EwsService ewsService, EventService eventService) {
		this.ewsService = ewsService;
		this.eventService = eventService;
	}

	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestParam String email, @RequestBody Event event) throws Exception {
		return eventService.createEvent(ewsService.impersonateUser(email), event);
	}

	@GetMapping
	public ResponseEntity<List<Event>> getEvents(@RequestParam String email, @RequestParam String startDateTime,
			@RequestParam String endDateTime) throws Exception {
		return eventService.getEvents(ewsService.impersonateUser(email), startDateTime, endDateTime);
	}

	@GetMapping({ "/{id}/**" })
	public ResponseEntity<Event> getEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return eventService.getEventById(ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
	}

	@GetMapping({ "/accept/{id}/**" })
	public ResponseEntity<Event> acceptEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return eventService.acceptEvent(ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
	}

	@GetMapping({ "/decline/{id}/**" })
	public ResponseEntity<Event> declineEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return eventService.declineEvent(ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
	}

	@GetMapping({ "/tentative/{id}/**" })
	public ResponseEntity<Event> tentativelyAcceptEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return eventService.tentativelyAcceptEvent(ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}
}
