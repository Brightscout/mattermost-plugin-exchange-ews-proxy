package com.brightscout.ews.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.service.EventService;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/event")
@Validated
public class EventController {

	private EwsService ewsService;

	private EventService eventService;

	@Autowired
	public EventController(EwsService ewsService, EventService eventService) {
		this.ewsService = ewsService;
		this.eventService = eventService;
	}

	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestParam @Email String email, @Valid @RequestBody Event event)
			throws InternalServerException {
		return eventService.createEvent(ewsService.impersonateUser(email), event);
	}

	@GetMapping
	public ResponseEntity<List<Event>> getEvents(@RequestParam @Email String email, @RequestParam String startDateTime,
			@RequestParam String endDateTime) throws InternalServerException {
		return eventService.getEvents(ewsService.impersonateUser(email), startDateTime, endDateTime);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<Event> getEvent(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return eventService.getEventById(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}

	@PostMapping({ "/{id}/accept" })
	public ResponseEntity<Event> acceptEvent(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return eventService.acceptEvent(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}

	@PostMapping({ "/{id}/decline" })
	public ResponseEntity<Event> declineEvent(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return eventService.declineEvent(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}

	@PostMapping({ "/{id}/tentative" })
	public ResponseEntity<Event> tentativelyAcceptEvent(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return eventService.tentativelyAcceptEvent(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}
}
