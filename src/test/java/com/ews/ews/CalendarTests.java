package com.ews.ews;

import com.brightscout.ews.controller.CalendarController;
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

    String email = "test-user@ad.brightscout.com";

    // Encoded calendar ID
    String calendarId = "TWFueSBoYW5kcyBtYWtlIGxpZ2h0IHdvcmsu";
    String encodedCalendarId = Base64.getEncoder().encodeToString(calendarId.getBytes());

    Calendar calendar_1 = new Calendar(encodedCalendarId, "test-calendar-1");
    Calendar calendar_2 = new Calendar(encodedCalendarId, "test-calendar-2");

    @Test
    public void getCalendars() {
        List<Calendar> calendars = new ArrayList<>(Arrays.asList(calendar_1, calendar_2));
        // expected response
        ResponseEntity<List<Calendar>> calendarsResponse = new ResponseEntity<>(calendars, HttpStatus.OK);
        Mockito.when(calendarService.getCalendars(ewsService.impersonateUser(email))).thenReturn(calendarsResponse);

        // call controller to be tested
        ResponseEntity<List<Calendar>> calendarsResult = calendarController.getCalendars(email);

        // compare results
        Assertions.assertEquals(HttpStatus.OK, calendarsResult.getStatusCode());
        Assertions.assertTrue(calendarsResult.equals(calendarsResponse));
    }

    @Test
    public void getCalendarById() {
        // expected response
        ResponseEntity<Calendar> calendarResponse = new ResponseEntity<>(calendar_1, HttpStatus.OK);
        Mockito.when(calendarService.getCalendar(ewsService.impersonateUser("abc@gm.com"), calendarId)).thenReturn(calendarResponse);

        // call controller to be tested
        ResponseEntity<Calendar> calendarResult = calendarController.getCalendar("abc@gm.com", encodedCalendarId);

        // compare results
        Assertions.assertEquals(HttpStatus.OK, calendarResponse.getStatusCode());
        Assertions.assertTrue(calendarResult.equals(calendarResponse));
    }
}
