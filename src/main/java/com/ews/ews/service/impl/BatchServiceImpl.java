package com.ews.ews.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.exception.InternalServerException;
import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.model.CalendarViewSingleRequest;
import com.ews.ews.model.CalendarViewSingleResponse;
import com.ews.ews.model.User;
import com.ews.ews.model.UserBatchSingleResponse;
import com.ews.ews.model.event.Event;
import com.ews.ews.service.BatchService;
import com.ews.ews.service.EventService;
import com.ews.ews.service.EwsService;
import com.ews.ews.service.UserService;

@Service
public class BatchServiceImpl implements BatchService {

	private EwsService ewsService;

	private EventService eventService;

	private UserService userService;

	public BatchServiceImpl(EwsService ewsService, EventService eventService, UserService userService) {
		this.ewsService = ewsService;
		this.eventService = eventService;
		this.userService = userService;
	}

	@Override
	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws Exception {
		List<CalendarViewSingleResponse> responses = new ArrayList<>();
		for (CalendarViewSingleRequest request : requests.getRequests()) {
			ResponseEntity<List<Event>> response = eventService.getEvents(
					ewsService.impersonateUser(request.getId()), request.getStartDateTime(), request.getEndDateTime());
			responses.add(new CalendarViewSingleResponse(request.getId(), response.getBody()));
		}

		return new ResponseEntity<>(new CalendarViewBatchResponse(responses), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(List<String> emails) throws Exception {
		List<UserBatchSingleResponse> users = new ArrayList<>();
		for (String email : emails) {
			try {
				ResponseEntity<User> user = userService.getUser(ewsService.impersonateUser(email), email);
				users.add(new UserBatchSingleResponse(user.getBody()));
			} catch (InternalServerException e) {
				users.add(new UserBatchSingleResponse(new User(email), e.getApiResponse()));
			}
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
