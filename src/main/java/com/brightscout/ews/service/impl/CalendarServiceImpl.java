package com.brightscout.ews.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.Calendar;
import com.brightscout.ews.model.FindMeetingTimesParameters;
import com.brightscout.ews.model.MeetingTimeSuggestion;
import com.brightscout.ews.model.MeetingTimeSuggestionResults;
import com.brightscout.ews.model.event.DateTime;
import com.brightscout.ews.model.event.EmailAddress;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.model.event.EventResponseStatus;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.CalendarService;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.FolderTraversal;
import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.availability.Suggestion;
import microsoft.exchange.webservices.data.property.complex.availability.TimeSuggestion;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

@Service
public class CalendarServiceImpl implements CalendarService {

	public Calendar getCalendarById(ExchangeService service, String calendarId) throws InternalServerException {
		try {
			FolderId folderId = new FolderId(calendarId);
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
			List<Appointment> appointments = itemResults.getItems();
			int eventsLen = appointments.size();
			Event[] events = new Event[eventsLen];

			for (int i = 0; i < eventsLen; i++) {
				Appointment appointment = appointments.get(i);
				Event event = new Event();
				event.setId(appointment.getId().toString());
				event.setCalUId(appointment.getICalUid());
				event.setSubject(appointment.getSubject());
				event.setImportance(appointment.getImportance().toString());
				event.setAllDay(appointment.getIsAllDayEvent());
				event.setCancelled(appointment.getIsCancelled());
				event.setReminderMinutesBeforeStart(appointment.getReminderMinutesBeforeStart());
				event.setResponseRequested(appointment.getIsResponseRequested());
				event.setStart(new DateTime(appointment.getStart().toString(), appointment.getTimeZone()));
				event.setEnd(new DateTime(appointment.getEnd().toString(), appointment.getTimeZone()));
				event.setLocation(appointment.getLocation());
				event.setShowAs(appointment.getLegacyFreeBusyStatus().toString());
				// TODO: Set message body in event
				// Attendees is combination of required and optional attendees in EWS
				List<Attendee> attendeesList = appointment.getRequiredAttendees().getItems();
				attendeesList.addAll(appointment.getOptionalAttendees().getItems());
				int attendeesLen = attendeesList.size();
				com.brightscout.ews.model.event.Attendee[] attendees = new com.brightscout.ews.model.event.Attendee[attendeesLen];
				for (int j = 0; j < attendeesLen; j++) {
					Attendee attendee = attendeesList.get(j);
					EventResponseStatus status = new EventResponseStatus(attendee.getResponseType().toString(),
							attendee.getLastResponseTime().toString());
					EmailAddress emailAddress = new EmailAddress(attendee.getAddress(), attendee.getName());
					// TODO: Need to populate correct type because this will affect find meeting
					// times functionality
					attendees[j] = new com.brightscout.ews.model.event.Attendee("required/optional", status, emailAddress);
				}
				event.setResponseStatus(new EventResponseStatus(appointment.getMyResponseType().toString()));
				event.setAttendees(attendees);
				microsoft.exchange.webservices.data.property.complex.EmailAddress organizerAddress = appointment
						.getOrganizer();
				event.setOrganizer(new com.brightscout.ews.model.event.Attendee(
						new EmailAddress(organizerAddress.getAddress(), organizerAddress.getName())));
				events[i] = event;
			}
			calendar.setEvents(events);
			calendar.setCalendarView(events);
			// TODO: Need to check for calendar view and user

			return calendar;
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while fetching calendar by id. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Calendar> createCalendar(ExchangeService service, Calendar calendar) throws InternalServerException {
		try {
			CalendarFolder folder = new CalendarFolder(service);
			folder.setDisplayName(calendar.getName());
			folder.save(WellKnownFolderName.Calendar);
			return new ResponseEntity<>(new Calendar(folder.getId().toString(), folder.getDisplayName()),
					HttpStatus.CREATED);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while creating calendar. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<List<Calendar>> getCalendars(ExchangeService service) throws InternalServerException {
		try {
			FolderId rfRootFolderid = new FolderId(WellKnownFolderName.Root);
			FolderView fvFolderView = new FolderView(1000);
			fvFolderView.setTraversal(FolderTraversal.Deep);
			fvFolderView.setPropertySet(new PropertySet(BasePropertySet.FirstClassProperties));
			SearchFilter sfSearchFilter = new SearchFilter.IsEqualTo(FolderSchema.FolderClass, "IPF.Appointment");
			FindFoldersResults ffoldres = service.findFolders(rfRootFolderid, sfSearchFilter, fvFolderView);
			String deletedFolderId = Folder.bind(service, WellKnownFolderName.DeletedItems).getId().getUniqueId();
			List<Calendar> calendars = new ArrayList<>();
			for (Folder folder : ffoldres.getFolders()) {
				if (!folder.getParentFolderId().getUniqueId().equals(deletedFolderId)) {
					calendars.add(getCalendarById(service, folder.getId().toString()));
				}
			}
			return new ResponseEntity<>(calendars, HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while fetching calendars. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Calendar> deleteCalendar(ExchangeService service, String calendarId) throws InternalServerException {
		try {
			FolderId folderId = new FolderId(calendarId);
			Folder folder = Folder.bind(service, folderId);
			Calendar calendar = new Calendar(calendarId, folder.getDisplayName());
			folder.delete(DeleteMode.HardDelete);
			return new ResponseEntity<>(calendar, HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while deleting calendar. Error: " + e.getMessage()));
		}
	}

	@Override
	public ResponseEntity<Calendar> getCalendar(ExchangeService service, String calendarId) throws InternalServerException {
		return new ResponseEntity<>(getCalendarById(service, calendarId), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<MeetingTimeSuggestionResults> findMeetingTimes(ExchangeService service, String organizerEmail,
			FindMeetingTimesParameters findMeetingTimes) throws InternalServerException {
		try {
			List<AttendeeInfo> attendees = new ArrayList<>();
			for (com.brightscout.ews.model.event.Attendee attendee : findMeetingTimes.getAttendees()) {
				MeetingAttendeeType attendeeType = AppConstants.MEETING_ATTENDEE_TYPE_MAP.get(attendee.getType());
				if (attendeeType == null) {
					continue;
				}
				attendees.add(new AttendeeInfo(attendee.getEmailAddress().getAddress(), attendeeType, false));
			}
			// Add organizer
			attendees.add(new AttendeeInfo(organizerEmail, MeetingAttendeeType.Organizer, false));

			java.util.Calendar cd = java.util.Calendar.getInstance();
			Date startDate = cd.getTime();
			cd.add(java.util.Calendar.DAY_OF_YEAR, 2);
			Date endDate = cd.getTime();
			GetUserAvailabilityResults results = service.getUserAvailability(attendees,
					new TimeWindow(startDate, endDate), AvailabilityData.Suggestions);
			List<MeetingTimeSuggestion> meetingTimes = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
			for (Suggestion suggestion : results.getSuggestions()) {
				for (TimeSuggestion timeSuggestion : suggestion.getTimeSuggestions()) {
					meetingTimes.add(new MeetingTimeSuggestion(
							new DateTime(dateFormat.format(timeSuggestion.getMeetingTime()), "")));
				}
			}

			return new ResponseEntity<>(new MeetingTimeSuggestionResults(meetingTimes), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while finding meeting times. Error: " + e.getMessage()));
		}
	}
}
