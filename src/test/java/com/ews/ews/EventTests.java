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

import java.time.LocalDate;
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

    // Dummy data
    String eventId = "TWFueSBoYW5kcyBtYWtlIGxpZ2h0IHdvcmsu";
    String email = "test-user@ad.brightscout.com";
    String dummyDate = LocalDate.now().toString();

    Event event;

    @BeforeEach
    public void setUp() {
        event = new Event(eventId);
    }

    @Test
    public void createEventSuccess() {
        ResponseEntity eventResponse = new ResponseEntity<>(event, HttpStatus.CREATED);
        Mockito.when(eventService.createEvent(ewsService.impersonateUser(email), event)).thenReturn(eventResponse);

        ResponseEntity eventResult = eventController.createEvent(email, event);
        Assertions.assertEquals(HttpStatus.CREATED, eventResult.getStatusCode());
        Assertions.assertTrue(eventResult.equals(eventResponse));
    }

    @Test
    public void createEventFailed() {
        Mockito.when(eventService.createEvent(ewsService.impersonateUser(email), event)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> eventController.createEvent(email, event));
    }

    @Test
    public void getEventsSuccess() {
        List<Event> events = new ArrayList<>(Arrays.asList(event, event));

        ResponseEntity<List<Event>> eventsResponse = new ResponseEntity<>(events, HttpStatus.OK);
        Mockito.when(eventService.getEvents(ewsService.impersonateUser(email), dummyDate, dummyDate)).thenReturn(eventsResponse);

        ResponseEntity<List<Event>> eventsResult = eventController.getEvents(email, dummyDate, dummyDate);
        Assertions.assertEquals(HttpStatus.OK, eventsResult.getStatusCode());
        Assertions.assertTrue(eventsResult.equals(eventsResponse));
    }

    @Test
    public void getEventsFailed() {
        Mockito.when(eventService.getEvents(ewsService.impersonateUser(email), dummyDate, dummyDate)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> eventController.getEvents(email, dummyDate, dummyDate));
    }
}
