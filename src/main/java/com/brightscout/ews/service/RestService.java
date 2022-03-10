package com.brightscout.ews.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.utils.AppConstants;

public interface RestService {

	@Retryable(value = Exception.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public void syncSubscriptions();

	@Retryable(value = Exception.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public boolean getSubscriptionByID(String subscriptionID);
}
