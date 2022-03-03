package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.model.subscribe.Subscription;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface SubscriptionService {

	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws Exception;

	public ResponseEntity<String> unsubscribeToStreamNotifications(Subscription subscribe)
			throws Exception;
}
