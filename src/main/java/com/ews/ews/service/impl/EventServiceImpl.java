package com.ews.ews.service.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.model.event.Event;
import com.ews.ews.service.EventService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.property.complex.time.TimeZoneDefinition;

@Service
public class EventServiceImpl implements EventService {

	@SuppressWarnings("deprecation")
	@Override
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws Exception {
		Appointment meeting = new Appointment(service);
		meeting.setSubject(event.getSubject());
//		meeting.setBody(new MessageBody(event.getBody().getContent()));
		meeting.setStart(new Date(event.getStart().getDateTime()));
		meeting.setEnd(new Date(event.getEnd().getDateTime()));
		TimeZoneDefinition timeZone = getTimeZone(service, event.getStart().getTimeZone());
		if (timeZone != null) {
			meeting.setStartTimeZone(timeZone);
			meeting.setEndTimeZone(timeZone);
		}
		meeting.setIsAllDayEvent(event.isAllDay());
		meeting.setReminderMinutesBeforeStart(event.getReminderMinutesBeforeStart());
		meeting.setLocation(event.getLocation());
		System.out.println(event.getSubject());
//		System.out.println(event.getBody().getContent());
		System.out.println(event.getStart().getDateTime());
		System.out.println(event.getEnd().getDateTime());
		System.out.println(timeZone);
		System.out.println(event.isAllDay());
		System.out.println(event.getReminderMinutesBeforeStart());
		System.out.println(event.getLocation());
		meeting.save(WellKnownFolderName.Calendar, SendInvitationsMode.SendOnlyToAll);
		
		// Populate meeting and calendar ID
//		event.setId(meeting.getId().toString());
//		event.setiCalUID(meeting.getICalUid());
		
		return new ResponseEntity<>(event, HttpStatus.CREATED);
	}
	
	public TimeZoneDefinition getTimeZone(ExchangeService service, String name) throws Exception {
		Collection<TimeZoneDefinition> timezones = service.getServerTimeZones();
		for (TimeZoneDefinition timezone : timezones) {
			if (timezone.getId().equalsIgnoreCase(name)) {
				return timezone;
			}
		}
		
		return null;
	}
	
}
