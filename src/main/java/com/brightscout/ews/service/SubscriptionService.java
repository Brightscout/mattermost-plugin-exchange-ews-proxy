package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscribe.Subscription;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface SubscriptionService {

	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws InternalServerException;
}
