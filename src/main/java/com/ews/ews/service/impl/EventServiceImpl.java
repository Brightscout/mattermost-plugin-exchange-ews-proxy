package com.ews.ews.service.impl;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.model.event.Event;
import com.ews.ews.service.EventService;
import com.ews.ews.utils.AppUtils;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.Attendee;

@Service
public class EventServiceImpl implements EventService {

	@Override
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws Exception {
		Appointment meeting = new Appointment(service);
		meeting.setSubject(event.getSubject());
		meeting.setStart(AppUtils.parseDateString(event.getStart().getDateTime()));
		meeting.setEnd(AppUtils.parseDateString(event.getEnd().getDateTime()));
		meeting.setIsAllDayEvent(event.isAllDay());
		meeting.setReminderMinutesBeforeStart(event.getReminderMinutesBeforeStart());
		meeting.setLocation(event.getLocation());
		for (com.ews.ews.model.event.Attendee attendee : event.getAttendees()) {
			meeting.getResources().add(new Attendee(attendee.getEmailAddress().getAddress()));
		}
		meeting.save(WellKnownFolderName.Calendar, SendInvitationsMode.SendOnlyToAll);

		// Populate meeting ID
		event.setId(meeting.getId().toString());

		return new ResponseEntity<>(event, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Event[]> getEvents(ExchangeService service, String start, String end) {
		
	}

}
