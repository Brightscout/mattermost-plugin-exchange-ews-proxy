package com.ews.ews.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.model.CalendarViewSingleRequest;
import com.ews.ews.model.CalendarViewSingleResponse;
import com.ews.ews.model.event.Event;
import com.ews.ews.service.BatchService;
import com.ews.ews.service.EWSService;
import com.ews.ews.service.EventService;
import com.ews.ews.utils.AppUtils;

import microsoft.exchange.webservices.data.core.ExchangeService;

@Service
public class BatchServiceImpl implements BatchService {

	@Autowired
	EventService eventService;

	@Autowired
	EWSService ewsService;

	@Override
	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws Exception {
		ArrayList<CalendarViewSingleResponse> responses = new ArrayList<>();
		for (CalendarViewSingleRequest request : requests.getRequests()) {
			ResponseEntity<ArrayList<Event>> response = this.eventService.getEvents(
					this.ewsService.impersonateUser(request.getId()), request.getStartDateTime(), request.getEndDateTime());
			responses.add(new CalendarViewSingleResponse(request.getId(), response.getBody()));
		}
		
		return new ResponseEntity<>(new CalendarViewBatchResponse(responses), HttpStatus.OK);
	}

}