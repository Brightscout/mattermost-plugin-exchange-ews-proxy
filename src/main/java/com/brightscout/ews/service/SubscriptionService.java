package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscription.Subscription;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface SubscriptionService {

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws InternalServerException;
}
