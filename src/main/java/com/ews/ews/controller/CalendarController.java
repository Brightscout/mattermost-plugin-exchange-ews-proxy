package com.ews.ews.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.ews.ews.model.FindMeetingTimesParameters;
import com.ews.ews.model.MeetingTimeSuggestionResults;
import com.ews.ews.service.CalendarService;
import com.ews.ews.service.EwsService;
import com.ews.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

	private EwsService ewsService;

	private CalendarService calendarService;

	public CalendarController(EwsService ewsService, CalendarService calendarService) {
		this.ewsService = ewsService;
		this.calendarService = calendarService;
	}

	@PostMapping
	public ResponseEntity<Calendar> createCalendar(@RequestParam String email, @Valid @RequestBody Calendar calendar)
			throws Exception {
		return calendarService.createCalendar(ewsService.impersonateUser(email), calendar);
	}

	@GetMapping
	public ResponseEntity<List<Calendar>> getCalendars(@RequestParam String email) throws Exception {
		return calendarService.getCalendars(ewsService.impersonateUser(email));
	}

	@GetMapping({ "/{id}/**" })
	public ResponseEntity<Calendar> getCalendar(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return calendarService.getCalendar(ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
	}

	@DeleteMapping({ "/{id}/**" })
	public ResponseEntity<Calendar> deleteCalendar(@RequestParam String email, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		return calendarService.deleteCalendar(ewsService.impersonateUser(email), AppUtils.getIdFromParams(id, request));
	}

	@PostMapping({ "/suggestions" })
	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(@RequestParam String email,
			@Valid @RequestBody FindMeetingTimesParameters findMeetingTimes) throws Exception {
		return calendarService.findMeetingTimes(ewsService.impersonateUser(email), email, findMeetingTimes);
	}
}
