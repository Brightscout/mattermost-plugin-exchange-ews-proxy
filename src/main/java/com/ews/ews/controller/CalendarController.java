package com.ews.ews.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.ews.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

	@Autowired
	EWSService ewsService;

	@Autowired
	CalendarService calendarService;

	@PostMapping
	public ResponseEntity<Calendar> createCalendar(@RequestParam String email, @RequestBody Calendar calendar)
			throws Exception {
		return this.calendarService.createCalendar(ewsService.impersonateUser(email), calendar);
	}

	@GetMapping
	public ResponseEntity<ArrayList<Calendar>> getCalendars(@RequestParam String email) throws Exception {
		return this.calendarService.getCalendars(ewsService.impersonateUser(email));
	}

	@GetMapping({ "/{id}/**" })
	public ResponseEntity<Calendar> getCalendar(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return this.calendarService.getCalendar(ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}

	@DeleteMapping({ "/{id}/**" })
	public ResponseEntity<Calendar> deleteCalendar(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return this.calendarService.deleteCalendar(ewsService.impersonateUser(email),
				AppUtils.getIdFromParams(id, request));
	}

}
