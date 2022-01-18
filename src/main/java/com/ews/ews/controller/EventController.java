package com.ews.ews.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.event.Event;
import com.ews.ews.service.EWSService;
import com.ews.ews.service.EventService;
import com.ews.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/event")
public class EventController {

	@Autowired
	EWSService ewsService;

	@Autowired
	EventService eventService;

	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestParam String email, @RequestBody Event event) throws Exception {
		return this.eventService.createEvent(this.ewsService.impersonateUser(email), event);
	}

	@GetMapping
	public ResponseEntity<ArrayList<Event>> getEvents(@RequestParam String email, @RequestParam String startDateTime,
			@RequestParam String endDateTime) throws Exception {
		return this.eventService.getEvents(this.ewsService.impersonateUser(email), startDateTime, endDateTime);
	}

	@GetMapping({ "/{id}/**" })
    public ResponseEntity<Event> getEventUsingNotificationId(@RequestParam String email, @PathVariable String id,
    HttpServletRequest request) throws Exception {
        return this.eventService.getEventUsingId(this.ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
    }

	@GetMapping({ "/accept/{id}/**" })
	public ResponseEntity<Event> acceptEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return this.eventService.acceptEvent(this.ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}

	@GetMapping({ "/decline/{id}/**" })
	public ResponseEntity<Event> declineEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return this.eventService.declineEvent(this.ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}

	@GetMapping({ "/tentative/{id}/**" })
	public ResponseEntity<Event> tentativelyAcceptEvent(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return this.eventService.tentativelyAcceptEvent(this.ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}

}
