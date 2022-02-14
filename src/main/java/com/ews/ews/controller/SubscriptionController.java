package com.ews.ews.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.subscribe.Subscription;
import com.ews.ews.service.EwsService;
import com.ews.ews.service.SubscriptionService;

@RestController
@RequestMapping("/api/notification")
public class SubscriptionController {

	private transient EwsService ewsService;

	private transient SubscriptionService subscriptionService;

	public SubscriptionController(EwsService ewsService, SubscriptionService subscriptionService) {
		this.ewsService = ewsService;
		this.subscriptionService = subscriptionService;
	}

	@PostMapping({ "/subscribe" })
	public ResponseEntity<Subscription> subscribeToStreamNotifications(@RequestParam String email,
			@RequestBody Subscription subscription) throws Exception {
		return subscriptionService.subscribeToStreamNotifications(ewsService.impersonateUser(email), subscription);
	}

	@PostMapping({ "/unsubscribe" })
	public ResponseEntity<String> unsubscribeToStreamNotifications(@RequestBody Subscription subscription)
			throws Exception {
		return subscriptionService.unsubscribeToStreamNotifications(subscription);
	}
}
