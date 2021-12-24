package com.ews.ews.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.model.Calendar;
import com.ews.ews.model.event.EmailAddress;
import com.ews.ews.model.event.Event;
import com.ews.ews.model.event.EventResponseStatus;
import com.ews.ews.model.event.ItemBody;
import com.ews.ews.service.CalendarService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

@Service
public class CalendarServiceImpl implements CalendarService {

	public ResponseEntity<Calendar> getCalendar(ExchangeService service, String id) throws Exception {
		FolderId folderId = new FolderId(id);
		CalendarFolder calendarFolder = CalendarFolder.bind(service, folderId, PropertySet.FirstClassProperties);
		Calendar calendar = new Calendar();
		calendar.setId(calendarFolder.getId().toString());
		calendar.setName(calendarFolder.getDisplayName());
		// TODO: Fetch limited number of results
		java.util.Calendar cd = java.util.Calendar.getInstance();
		Date startDate = cd.getTime();
		cd.add(java.util.Calendar.DAY_OF_YEAR, 1);
		Date endDate = cd.getTime();
		CalendarView calView = new CalendarView(startDate, endDate);
		FindItemsResults<Appointment> itemResults = calendarFolder.findAppointments(calView);
		ArrayList<Appointment> appointments = itemResults.getItems();
		int eventsLen = appointments.size();
		Event[] events = new Event[eventsLen];

		for (int i = 0; i < eventsLen; i++) {
			Appointment appointment = appointments.get(i);
			Event event = new Event();
			event.setId(appointment.getId().toString());
			event.setiCalUID(appointment.getICalUid());
			event.setSubject(appointment.getSubject());
			MessageBody messageBody = appointment.getBody();
			event.setBody(new ItemBody(MessageBody.getStringFromMessageBody(messageBody),
					messageBody.getBodyType().toString()));
			event.setImportance(appointment.getImportance().toString());
			event.setAllDay(appointment.getIsAllDayEvent());
			event.setCancelled(appointment.getIsCancelled());
			event.setResponseRequested(appointment.getIsResponseRequested());
			event.setStart(appointment.getStart());
			event.setEnd(appointment.getEnd());
			event.setLocation(appointment.getLocation());
			// Attendees is combination of required and optional attendees in EWS
			List<Attendee> attendeesList = appointment.getRequiredAttendees().getItems();
			attendeesList.addAll(appointment.getOptionalAttendees().getItems());
			int attendeesLen = attendeesList.size();
			com.ews.ews.model.event.Attendee[] attendees = new com.ews.ews.model.event.Attendee[attendeesLen];
			for (int j = 0; j < attendeesLen; j++) {
				Attendee attendee = attendeesList.get(j);
				EventResponseStatus status = new EventResponseStatus(attendee.getResponseType().toString(),
						attendee.getLastResponseTime().toString());
				EmailAddress emailAddress = new EmailAddress(attendee.getAddress(), attendee.getName());
				// TODO: Need to populate correct type because this will affect find meeting
				// times functionality
				attendees[j] = new com.ews.ews.model.event.Attendee("required/optional", status, emailAddress);
			}
			event.setResponseStatus(new EventResponseStatus(appointment.getMyResponseType().toString(),
					appointment.getLastModifiedTime().toString()));
			event.setAttendees(attendees);
			microsoft.exchange.webservices.data.property.complex.EmailAddress organizerAddress = appointment
					.getOrganizer();
			event.setOrganizer(new com.ews.ews.model.event.Attendee("organizer", new EventResponseStatus(),
					new EmailAddress(organizerAddress.getAddress(), organizerAddress.getName())));
			events[i] = event;
		}
		calendar.setEvents(events);
		calendar.setCalendarView(events);
		// TODO: Need to check for calendar view and user

		return new ResponseEntity<>(calendar, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws Exception {
		CalendarFolder folder = new CalendarFolder(service);
		folder.setDisplayName(calendar.getName());
		folder.save(WellKnownFolderName.Calendar);

		return new ResponseEntity<>(new Calendar(folder.getId().toString(), folder.getDisplayName()),
				HttpStatus.CREATED);
	}

}
