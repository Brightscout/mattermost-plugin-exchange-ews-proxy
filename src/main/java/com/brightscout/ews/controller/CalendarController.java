package com.brightscout.ews.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.Calendar;
import com.brightscout.ews.model.FindMeetingTimesParameters;
import com.brightscout.ews.model.MeetingTimeSuggestionResults;
import com.brightscout.ews.service.CalendarService;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.utils.AppUtils;

@RestController
@RequestMapping("/api/calendar")
@Validated
public class CalendarController {

	private EwsService ewsService;

	private CalendarService calendarService;

	@Autowired
	public CalendarController(EwsService ewsService, CalendarService calendarService) {
		this.ewsService = ewsService;
		this.calendarService = calendarService;
	}

	@PostMapping
	public ResponseEntity<Calendar> createCalendar(@RequestParam @Email String email,
			@Valid @RequestBody Calendar calendar) throws InternalServerException {
		return calendarService.createCalendar(ewsService.impersonateUser(email), calendar);
	}

	@GetMapping
	public ResponseEntity<List<Calendar>> getCalendars(@RequestParam @Email String email)
			throws InternalServerException {
		return calendarService.getCalendars(ewsService.impersonateUser(email));
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<Calendar> getCalendar(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return calendarService.getCalendar(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}

	@DeleteMapping({ "/{id}" })
	public ResponseEntity<Calendar> deleteCalendar(@RequestParam @Email String email, @PathVariable String id)
			throws InternalServerException {
		return calendarService.deleteCalendar(ewsService.impersonateUser(email), AppUtils.decodeBase64String(id));
	}

	@PostMapping({ "/suggestions" })
	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(@RequestParam @Email String email,
			@Valid @RequestBody FindMeetingTimesParameters findMeetingTimes) throws InternalServerException {
		return calendarService.findMeetingTimes(ewsService.impersonateUser(email), email, findMeetingTimes);
	}
}
