package com.ews.ews;

import com.brightscout.ews.controller.BatchController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.CalendarViewBatchRequest;
import com.brightscout.ews.model.CalendarViewBatchResponse;
import com.brightscout.ews.model.CalendarViewSingleRequest;
import com.brightscout.ews.model.CalendarViewSingleResponse;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.service.BatchService;
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
public class BatchTests {
    @InjectMocks
    private BatchController batchController;

    @Mock
    private BatchService batchService;

    // Dummy data
    String dummyId = "TWFueSBoYW5kcyBtYWtlIGxpZ2h0IHdvcmsu";
    String dummyDate = LocalDate.now().toString();

    CalendarViewBatchRequest batchRequest;

    @BeforeEach
    public void setUp() {
        CalendarViewSingleRequest calendarViewSingleRequest = new CalendarViewSingleRequest();
        calendarViewSingleRequest.setId(dummyId);
        calendarViewSingleRequest.setStartDateTime(dummyDate);
        calendarViewSingleRequest.setEndDateTime(dummyDate);

        List<CalendarViewSingleRequest> calendarViewSingleRequests = new ArrayList<>(Arrays.asList(calendarViewSingleRequest));
        batchRequest = new CalendarViewBatchRequest();
        batchRequest.setRequests(calendarViewSingleRequests);
    }

    @Test
    public void getEventsSuccess() {
        Event event1 = new Event(dummyId);
        List<Event> events = new ArrayList<>(Arrays.asList(event1, event1));

        CalendarViewSingleResponse calendarViewSingleResponse = new CalendarViewSingleResponse(dummyId, events);
        List<CalendarViewSingleResponse> calendarViewSingleResponses = new ArrayList<>(Arrays.asList(calendarViewSingleResponse));
        CalendarViewBatchResponse batchResponse = new CalendarViewBatchResponse(calendarViewSingleResponses);

        ResponseEntity<CalendarViewBatchResponse> getEventResponse = new ResponseEntity<>(batchResponse, HttpStatus.OK);

        Mockito.when(batchService.getEvents(batchRequest)).thenReturn(getEventResponse);
        ResponseEntity<CalendarViewBatchResponse> getEventResult = batchController.getEvents(batchRequest);

        Assertions.assertEquals(HttpStatus.OK, getEventResult.getStatusCode());
        Assertions.assertTrue(getEventResult.equals(getEventResponse));
    }

    @Test
    public void getEventsFailed() {
        Mockito.when(batchService.getEvents(batchRequest)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> batchController.getEvents(batchRequest));
    }
}
