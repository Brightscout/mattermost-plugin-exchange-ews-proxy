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
import com.ews.ews.model.User;
import com.ews.ews.model.event.Event;
import com.ews.ews.service.BatchService;
import com.ews.ews.service.EWSService;
import com.ews.ews.service.EventService;
import com.ews.ews.service.UserService;

@Service
public class BatchServiceImpl implements BatchService {

	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;

	@Autowired
	EWSService ewsService;

	@Override
	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws Exception {
		ArrayList<CalendarViewSingleResponse> responses = new ArrayList<>();
		for (CalendarViewSingleRequest request : requests.getRequests()) {
			ResponseEntity<ArrayList<Event>> response = this.eventService.getEvents(
					this.ewsService.impersonateUser(request.getId()), request.getStartDateTime(),
					request.getEndDateTime());
			responses.add(new CalendarViewSingleResponse(request.getId(), response.getBody()));
		}

		return new ResponseEntity<>(new CalendarViewBatchResponse(responses), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ArrayList<User>> getUsers(ArrayList<String> emails) throws Exception {
		ArrayList<User> users = new ArrayList<User>();
		for (String email : emails) {
			try {
				ResponseEntity<User> user = this.userService.getUser(this.ewsService.impersonateUser(email), email);
				users.add(user.getBody());
			} catch(Exception e) {
				System.out.printf("error occurred while fetching user deails for email: %s. Error: %s", email, e.getMessage());
			}
		}
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
