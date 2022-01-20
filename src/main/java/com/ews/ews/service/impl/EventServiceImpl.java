package com.ews.ews.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.model.event.DateTime;
import com.ews.ews.model.event.Event;
import com.ews.ews.service.EventService;
import com.ews.ews.utils.AppConstants;
import com.ews.ews.utils.AppUtils;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.schema.AppointmentSchema;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

@Service
public class EventServiceImpl implements EventService {

	@Override
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws Exception {
		Appointment meeting = new Appointment(service);
		meeting.setSubject(event.getSubject());
		meeting.setBody(new MessageBody(event.getBody().getContent()));
		meeting.setStart(AppUtils.parseDateString(event.getStart().getDateTime()));
		meeting.setEnd(AppUtils.parseDateString(event.getEnd().getDateTime()));
		meeting.setIsAllDayEvent(event.isAllDay());
		meeting.setReminderMinutesBeforeStart(event.getReminderMinutesBeforeStart());
		meeting.setLocation(event.getLocation());
		if (event.getAttendees() != null) {
			for (com.ews.ews.model.event.Attendee attendee : event.getAttendees()) {
				meeting.getResources().add(new Attendee(attendee.getEmailAddress().getAddress()));
			}
		}
		meeting.save(WellKnownFolderName.Calendar, SendInvitationsMode.SendOnlyToAll);

		// Populate meeting ID
		event.setId(meeting.getId().toString());

		return new ResponseEntity<>(event, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ArrayList<Event>> getEvents(ExchangeService service, String start, String end)
			throws Exception {
		CalendarFolder calendar = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
		CalendarView calView = new CalendarView(AppUtils.parseDateString(start), AppUtils.parseDateString(end), AppConstants.MAX_NUMBER_OF_EVENTS);
		calView.setPropertySet(new PropertySet(AppointmentSchema.Subject, AppointmentSchema.Start,
				AppointmentSchema.End, AppointmentSchema.TimeZone));
		FindItemsResults<Appointment> appointments = calendar.findAppointments(calView);
		ArrayList<Event> events = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		for (Appointment appointment : appointments) {
			Event event = new Event();
			event.setStart(new DateTime(f.format(appointment.getStart()).toString(), appointment.getTimeZone()));
			event.setEnd(new DateTime(f.format(appointment.getEnd()).toString(), appointment.getTimeZone()));
			event.setSubject(appointment.getSubject().toString());
			events.add(event);
		}

		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Event> acceptEvent(ExchangeService service, String eventId) throws Exception {
		Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
		appointment.accept(true);

		return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Event> declineEvent(ExchangeService service, String eventId) throws Exception {
		Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
		appointment.decline(true);

		return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Event> tentativelyAcceptEvent(ExchangeService service, String eventId) throws Exception {
		Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
		appointment.acceptTentatively(true);

		return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
	}

}