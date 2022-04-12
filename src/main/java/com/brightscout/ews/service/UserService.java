package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.User;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface UserService {

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<User> getUser(ExchangeService service, String email) throws InternalServerException;
}
