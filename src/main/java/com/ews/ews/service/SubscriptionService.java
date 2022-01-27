package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import microsoft.exchange.webservices.data.core.ExchangeService;

import com.ews.ews.model.subscribe.Subscription;

public interface SubscriptionService {
	public ResponseEntity<Subscribe> subscribeToStreamNotifications(ExchangeService service, Subscribe subscribe)
			throws Exception;
	public ResponseEntity<String> unSubscribeToStreamNotifications(Subscribe subscribe)
			throws Exception;
}
