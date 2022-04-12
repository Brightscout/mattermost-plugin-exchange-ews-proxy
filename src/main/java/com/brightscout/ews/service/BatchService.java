package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.CalendarViewBatchRequest;
import com.brightscout.ews.model.CalendarViewBatchResponse;
import com.brightscout.ews.model.UserBatchSingleResponse;
import com.brightscout.ews.model.subscription.SubscriptionBatchSingleRequest;
import com.brightscout.ews.model.subscription.SubscriptionBatchSingleResponse;
import com.brightscout.ews.utils.AppConstants;

public interface BatchService {

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests)
			throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(List<String> emails) throws InternalServerException;

	@Retryable(value = InternalServerException.class, backoff = @Backoff(AppConstants.WAIT_INTERVAL_IN_MILLISECONDS))
	public ResponseEntity<List<SubscriptionBatchSingleResponse>> getSubscriptions(
			List<SubscriptionBatchSingleRequest> requests) throws InternalServerException;
}
