package com.ews.ews;

import com.brightscout.ews.controller.CalendarController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.Calendar;
import com.brightscout.ews.service.CalendarService;
import com.brightscout.ews.service.EwsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CalendarTests {
    // controller for which we want to use the mock services
    @InjectMocks
    private CalendarController calendarController;

    // create mock services
    @Mock
    private CalendarService calendarService;

    @Mock
    private EwsService ewsService;

    // Encoded calendar ID
    String encodedCalendarId = Base64.getEncoder().encodeToString(TestUtils.ID.getBytes());

    Calendar calendar_1 = new Calendar(encodedCalendarId, "test-calendar-1");
    Calendar calendar_2 = new Calendar(encodedCalendarId, "test-calendar-2");

    @Test
    public void getCalendarsSuccess() {
        List<Calendar> calendars = new ArrayList<>(Arrays.asList(calendar_1, calendar_2));
        // expected response
        ResponseEntity<List<Calendar>> calendarsResponse = new ResponseEntity<>(calendars, HttpStatus.OK);
        Mockito.when(calendarService.getCalendars(ewsService.impersonateUser(TestUtils.EMAIL))).thenReturn(calendarsResponse);

        // call controller to be tested
        ResponseEntity<List<Calendar>> calendarsResult = calendarController.getCalendars(TestUtils.EMAIL);

        // compare results
        Assertions.assertTrue(calendarsResult.equals(calendarsResponse));
    }

    @Test
    public void getCalendarsFailed() {
        Mockito.when(calendarService.getCalendars(ewsService.impersonateUser(TestUtils.EMAIL))).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> calendarController.getCalendars(TestUtils.EMAIL));
    }

    @Test
    public void getCalendarByIdSuccess() {
        // expected response
        ResponseEntity<Calendar> calendarResponse = new ResponseEntity<>(calendar_1, HttpStatus.OK);
        Mockito.when(calendarService.getCalendar(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.ID)).thenReturn(calendarResponse);

        // call controller to be tested
        ResponseEntity<Calendar> calendarResult = calendarController.getCalendar(TestUtils.EMAIL, encodedCalendarId);

        // compare results
        Assertions.assertTrue(calendarResult.equals(calendarResponse));
    }

    @Test
    public void getCalendarByIdFailed() {
        Mockito.when(calendarService.getCalendar(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.ID)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> calendarController.getCalendar(TestUtils.EMAIL, encodedCalendarId));
    }
}
