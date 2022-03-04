package com.brightscout.ews.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.event.DateTime;
import com.brightscout.ews.model.event.EmailAddress;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.model.event.EventResponseStatus;
import com.brightscout.ews.model.event.ItemBody;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.EventService;
import com.brightscout.ews.utils.AppConstants;
import com.brightscout.ews.utils.AppUtils;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.ResponseActions;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

@Service
public class EventServiceImpl implements EventService {

	private List<com.brightscout.ews.model.event.Attendee> getAttendee(List<Attendee> attendeesList, MeetingAttendeeType attendeeType) {
		List<com.brightscout.ews.model.event.Attendee> attendees = new ArrayList<>();
		for (Attendee attendee : attendeesList) {
			EventResponseStatus status = new EventResponseStatus(attendee.getResponseType().toString());
			EmailAddress emailAddress = new EmailAddress(attendee.getAddress(), attendee.getName());
			attendees.add(new com.brightscout.ews.model.event.Attendee(attendeeType.toString(), status, emailAddress));
		}

		return attendees;
	}

	private Event getEventFromAppointment(Appointment appointment) throws Exception {
		Event event = new Event();
		appointment.load();
		event.setId(appointment.getId().toString());
		event.setCalUId(appointment.getICalUid());
		event.setSubject(appointment.getSubject().toString());
		event.setBody(new ItemBody(appointment.getBody().toString()));
		SimpleDateFormat dateFormat = AppUtils.getDateFormat();
		event.setStart(new DateTime(dateFormat.format(appointment.getStart()).toString()));
		event.setEnd(new DateTime(dateFormat.format(appointment.getEnd()).toString()));
		event.setShowAs(appointment.getLegacyFreeBusyStatus().toString());
		event.setCancelled(appointment.getIsCancelled());
		event.setResponseRequested(appointment.getIsResponseRequested());
		event.setImportance(appointment.getImportance().toString());
		event.setLocation(appointment.getLocation());
		event.setAllDay(appointment.getIsAllDayEvent());
		event.setWebLink(appointment.getNetShowUrl());
		event.setAttendeeOrganizer(!appointment.getAllowedResponseActions().contains(ResponseActions.Accept));
		event.setResponseStatus(new EventResponseStatus(appointment.getMyResponseType().toString()));
		event.setOrganizer(new com.brightscout.ews.model.event.Attendee(
				new EmailAddress(appointment.getOrganizer().getAddress(), appointment.getOrganizer().getName())));
		event.setReminderMinutesBeforeStart(appointment.getReminderMinutesBeforeStart());
		List<com.brightscout.ews.model.event.Attendee> attendees = new ArrayList<>();
		attendees.addAll(getAttendee(appointment.getRequiredAttendees().getItems(), MeetingAttendeeType.Required));
		attendees.addAll(getAttendee(appointment.getOptionalAttendees().getItems(), MeetingAttendeeType.Optional));
		attendees.addAll(getAttendee(appointment.getResources().getItems(), MeetingAttendeeType.Resource));
		event.setAttendees(attendees);

		return event;
	}
	
	@Override
	public ResponseEntity<Event> createEvent(ExchangeService service, Event event) throws InternalServerException {
		try {
			Appointment meeting = new Appointment(service);
			meeting.setSubject(event.getSubject());
			meeting.setBody(new MessageBody(event.getBody().getContent()));
			meeting.setStart(AppUtils.parseDateString(event.getStart().getDate()));
			meeting.setEnd(AppUtils.parseDateString(event.getEnd().getDate()));
			meeting.setIsAllDayEvent(event.isAllDay());
			meeting.setReminderMinutesBeforeStart(event.getReminderMinutesBeforeStart());
			meeting.setLocation(event.getLocation());
			if (event.getAttendees() != null) {
				for (com.brightscout.ews.model.event.Attendee attendee : event.getAttendees()) {
					meeting.getRequiredAttendees().add(new Attendee(attendee.getEmailAddress().getAddress()));
				}
			}
			meeting.save(WellKnownFolderName.Calendar, SendInvitationsMode.SendOnlyToAll);

			// Populate meeting ID
			event.setId(meeting.getId().toString());

			return new ResponseEntity<>(event, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while creating event. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<List<Event>> getEvents(ExchangeService service, String start, String end)
			throws InternalServerException {
		try {
			CalendarFolder calendar = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
			CalendarView calView = new CalendarView(AppUtils.parseDateString(start), AppUtils.parseDateString(end),
					AppConstants.MAX_NUMBER_OF_EVENTS);
			FindItemsResults<Appointment> appointments = calendar.findAppointments(calView);
			List<Event> events = new ArrayList<>();
			for (Appointment appointment : appointments) {
				events.add(getEventFromAppointment(appointment));
			}

			return new ResponseEntity<>(events, HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while fetching events. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Event> acceptEvent(ExchangeService service, String eventId) throws InternalServerException {
		try {
			Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
			appointment.accept(true);

			return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while accepting event" + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Event> declineEvent(ExchangeService service, String eventId) throws InternalServerException {
		try {
			Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
			appointment.decline(true);

			return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while declining event. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Event> tentativelyAcceptEvent(ExchangeService service, String eventId) throws InternalServerException {
		try {
			Appointment appointment = Appointment.bind(service, new ItemId(eventId), new PropertySet());
			appointment.acceptTentatively(true);

			return new ResponseEntity<>(new Event(eventId), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while tentatively accepting event. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Event> getEventById(ExchangeService service, String id) throws InternalServerException {
		try {
			return new ResponseEntity<Event>(getEventFromAppointment(Appointment.bind(service, new ItemId(id))), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while fetching event by id. Error: " + e.getMessage()));
		}
	}
}
