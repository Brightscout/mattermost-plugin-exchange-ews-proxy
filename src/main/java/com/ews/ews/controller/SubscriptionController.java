package com.ews.ews.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.subscribe.Subscription;
import com.ews.ews.service.EWSService;
import com.ews.ews.service.SubscriptionService;

@RestController
@RequestMapping("/api/notification")
public class SubscriptionController {

	private EWSService ewsService;

	private SubscriptionService subscriptionService;

	public SubscriptionController(EWSService ewsService, SubscriptionService subscriptionService) {
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
