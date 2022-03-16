package com.brightscout.ews.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.CalendarViewBatchRequest;
import com.brightscout.ews.model.CalendarViewBatchResponse;
import com.brightscout.ews.model.CalendarViewSingleRequest;
import com.brightscout.ews.model.CalendarViewSingleResponse;
import com.brightscout.ews.model.User;
import com.brightscout.ews.model.UserBatchSingleResponse;
import com.brightscout.ews.model.event.Event;
import com.brightscout.ews.model.subscription.Subscription;
import com.brightscout.ews.model.subscription.SubscriptionBatchSingleRequest;
import com.brightscout.ews.model.subscription.SubscriptionBatchSingleResponse;
import com.brightscout.ews.service.BatchService;
import com.brightscout.ews.service.EventService;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.SubscriptionService;
import com.brightscout.ews.service.UserService;

@Service
public class BatchServiceImpl implements BatchService {

	private EwsService ewsService;

	private EventService eventService;

	private UserService userService;

	private SubscriptionService subscriptionService;

	@Autowired
	public BatchServiceImpl(EwsService ewsService, EventService eventService, UserService userService, SubscriptionService subscriptionService) {
		this.ewsService = ewsService;
		this.eventService = eventService;
		this.userService = userService;
		this.subscriptionService = subscriptionService;
	}

	@Override
	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws InternalServerException {
		List<CalendarViewSingleResponse> responses = new ArrayList<>();
		for (CalendarViewSingleRequest request : requests.getRequests()) {
			ResponseEntity<List<Event>> response = eventService.getEvents(
					ewsService.impersonateUser(request.getId()), request.getStartDateTime(), request.getEndDateTime());
			responses.add(new CalendarViewSingleResponse(request.getId(), response.getBody()));
		}

		return new ResponseEntity<>(new CalendarViewBatchResponse(responses), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(List<String> emails) throws InternalServerException {
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

	@Override
	public ResponseEntity<List<SubscriptionBatchSingleResponse>> getSubscriptions(List<SubscriptionBatchSingleRequest> requests)
			throws InternalServerException {
		List<SubscriptionBatchSingleResponse> subscriptions = new ArrayList<>();
		for (SubscriptionBatchSingleRequest request : requests) {
			try {
				ResponseEntity<Subscription> subscription = subscriptionService.subscribeToStreamNotifications(
						ewsService.impersonateUser(request.getEmail()), request.getSubscription());
				subscriptions.add(new SubscriptionBatchSingleResponse(request.getEmail(), subscription.getBody()));
			} catch (InternalServerException e) {
				subscriptions.add(new SubscriptionBatchSingleResponse(request.getEmail(), e.getApiResponse()));
			}
		}

		return new ResponseEntity<>(subscriptions, HttpStatus.OK);
	}
}
