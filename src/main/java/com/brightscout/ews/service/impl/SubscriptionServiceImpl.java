package com.brightscout.ews.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscription.Subscription;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.SubscriptionService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.notification.PushSubscription;
import microsoft.exchange.webservices.data.property.complex.FolderId;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private int subscriptionLifetimeInMinutes;

	public SubscriptionServiceImpl(@Value("${app.subscriptionLifetimeInMinutes}") int subscriptionLifetimeInMinutes) {
		this.subscriptionLifetimeInMinutes = subscriptionLifetimeInMinutes;
	}

	@Override
	public ResponseEntity<Subscription> subscribeToPushNotifications(ExchangeService service, Subscription subscription)
			throws InternalServerException {
		try {
			// Get all folderIds of the user
			List<FolderId> folderIds = new ArrayList<FolderId>();
			folderIds.add(new FolderId(WellKnownFolderName.Calendar));

			// Create push subscription for user
			PushSubscription pushSubscription = service.subscribeToPushNotifications(folderIds,
					new URI(subscription.getWebhookNotificationUrl()), subscriptionLifetimeInMinutes, null, EventType.Created);

			return new ResponseEntity<Subscription>(new Subscription(pushSubscription.getId()), HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while creating subscription. Error: " + e.getMessage()));
		}
	}
}
