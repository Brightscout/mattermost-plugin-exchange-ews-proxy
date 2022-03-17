package com.brightscout.ews.controller;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscription.Subscription;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.SubscriptionService;

@RestController
@RequestMapping("/api/notification")
@Validated
public class SubscriptionController {

	private EwsService ewsService;

	private SubscriptionService subscriptionService;

	@Autowired
	public SubscriptionController(EwsService ewsService, SubscriptionService subscriptionService) {
		this.ewsService = ewsService;
		this.subscriptionService = subscriptionService;
	}

	@PostMapping({ "/subscribe" })
	public ResponseEntity<Subscription> subscribeToStreamNotifications(@RequestParam @Email String email,
			@RequestBody Subscription subscription) throws InternalServerException {
		return subscriptionService.subscribeToStreamNotifications(ewsService.impersonateUser(email), subscription);
	}
}
