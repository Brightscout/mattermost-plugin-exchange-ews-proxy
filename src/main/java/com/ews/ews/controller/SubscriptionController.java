package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
	EWSService ewsService;

    @Autowired
    SubscriptionService subscriptionService;

	@PostMapping({"/subscribe"})
	public ResponseEntity<Subscribe> subscribeToStreamNotifications(@RequestParam String email,
			@RequestBody Subscribe subscribe) throws Exception {
		return this.subscriptionService.subscribeToStreamNotifications(this.ewsService.impersonateUser(email),
				subscribe);
	}

	@PostMapping({"/unsubscribe"})
	public ResponseEntity<String> unSubscribeToStreamNotifications(@RequestBody Subscribe subscribe)
	throws Exception {
		return this.subscriptionService.unSubscribeToStreamNotifications(subscribe);
	}

}
