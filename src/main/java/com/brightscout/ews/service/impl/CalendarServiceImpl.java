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
<<<<<<< HEAD
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.CalendarService;
import com.brightscout.ews.utils.AppConstants;
import com.brightscout.ews.utils.AppUtils;
=======
import com.brightscout.ews.model.event.EmailAddress;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.model.event.EventResponseStatus;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.CalendarService;
import com.brightscout.ews.utils.AppConstants;
>>>>>>> main_1

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
<<<<<<< HEAD
=======
import microsoft.exchange.webservices.data.core.service.item.Appointment;
>>>>>>> main_1
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
<<<<<<< HEAD
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.availability.Suggestion;
import microsoft.exchange.webservices.data.property.complex.availability.TimeSuggestion;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
=======
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.availability.Suggestion;
import microsoft.exchange.webservices.data.property.complex.availability.TimeSuggestion;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
>>>>>>> main_1
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

@Service
public class CalendarServiceImpl implements CalendarService {

	public Calendar getCalendarById(ExchangeService service, String calendarId) throws Exception {
		CalendarFolder calendarFolder = CalendarFolder.bind(service, new FolderId(calendarId), PropertySet.FirstClassProperties);
		Calendar calendar = new Calendar(calendarFolder.getId().toString(), calendarFolder.getDisplayName());

		return calendar;
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
		try {
			return new ResponseEntity<>(getCalendarById(service, calendarId), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while fetching calendar. Error: " + e.getMessage()));
		}
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

			java.util.Calendar cd = java.util.Calendar.getInstance();
			Date startDate = cd.getTime();
			cd.add(java.util.Calendar.DAY_OF_YEAR, 2);
			Date endDate = cd.getTime();
			GetUserAvailabilityResults results = service.getUserAvailability(attendees,
					new TimeWindow(startDate, endDate), AvailabilityData.Suggestions);
			List<MeetingTimeSuggestion> meetingTimes = new ArrayList<>();
			SimpleDateFormat dateFormat = AppUtils.getDateFormat();
			for (Suggestion suggestion : results.getSuggestions()) {
				for (TimeSuggestion timeSuggestion : suggestion.getTimeSuggestions()) {
					meetingTimes.add(new MeetingTimeSuggestion(
							new DateTime(dateFormat.format(timeSuggestion.getMeetingTime()))));
				}
			}

			return new ResponseEntity<>(new MeetingTimeSuggestionResults(meetingTimes), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while finding meeting times. Error: " + e.getMessage()));
		}
	}
}
