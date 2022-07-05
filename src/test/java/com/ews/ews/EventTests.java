package com.ews.ews;

import com.brightscout.ews.controller.EventController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.service.EventService;
import com.brightscout.ews.service.EwsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventTests {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EwsService ewsService;

    @Mock
    private EventService eventService;

    Event event;

    @BeforeEach
    public void setUp() {
        event = new Event(TestUtils.ID);
    }

    @Test
    public void createEventSuccess() {
        ResponseEntity<Event> eventResponse = new ResponseEntity<>(event, HttpStatus.CREATED);
        Mockito.when(eventService.createEvent(ewsService.impersonateUser(TestUtils.EMAIL), event)).thenReturn(eventResponse);

        ResponseEntity<Event> eventResult = eventController.createEvent(TestUtils.EMAIL, event);
        Assertions.assertTrue(eventResult.equals(eventResponse));
    }

    @Test
    public void createEventFailed() {
        Mockito.when(eventService.createEvent(ewsService.impersonateUser(TestUtils.EMAIL), event)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> eventController.createEvent(TestUtils.EMAIL, event));
    }

    @Test
    public void getEventsSuccess() {
        List<Event> events = new ArrayList<>(Arrays.asList(event, event));

        ResponseEntity<List<Event>> eventsResponse = new ResponseEntity<>(events, HttpStatus.OK);
        Mockito.when(eventService.getEvents(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.DUMMY_DATE, TestUtils.DUMMY_DATE)).thenReturn(eventsResponse);

        ResponseEntity<List<Event>> eventsResult = eventController.getEvents(TestUtils.EMAIL, TestUtils.DUMMY_DATE, TestUtils.DUMMY_DATE);
        Assertions.assertTrue(eventsResult.equals(eventsResponse));
    }

    @Test
    public void getEventsFailed() {
        Mockito.when(eventService.getEvents(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.DUMMY_DATE, TestUtils.DUMMY_DATE)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> eventController.getEvents(TestUtils.EMAIL, TestUtils.DUMMY_DATE, TestUtils.DUMMY_DATE));
    }
}
