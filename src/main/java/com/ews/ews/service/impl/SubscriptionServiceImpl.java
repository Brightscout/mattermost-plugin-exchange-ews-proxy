package com.ews.ews.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ews.ews.exception.InternalServerException;
import com.ews.ews.model.subscribe.SubscribeNotificationResponse;
import com.ews.ews.model.subscribe.Subscription;
import com.ews.ews.payload.ApiResponse;
import com.ews.ews.service.SubscriptionService;
import com.ews.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.notification.ItemEvent;
import microsoft.exchange.webservices.data.notification.NotificationEvent;
import microsoft.exchange.webservices.data.notification.NotificationEventArgs;
import microsoft.exchange.webservices.data.notification.StreamingSubscription;
import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import microsoft.exchange.webservices.data.notification.SubscriptionErrorEventArgs;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.ItemId;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	@Override
	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws Exception {
		try {
			List<FolderId> folderIds = new ArrayList<FolderId>();
			folderIds.add(new FolderId(WellKnownFolderName.Calendar));

			final StreamingSubscription streamingSubscription = service.subscribeToStreamingNotifications(folderIds,
					EventType.Created
			// TODO: confirm all required types
			// EventType.Deleted,
			// EventType.Modified
			);

			// Create a streaming connection to the service object, over which events are
			// returned to the client.
			// Keep the streaming connection open for 30 minutes.
			// TODO: add logic to close the connection
			final StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service,
					AppConstants.SUBSCRIPTION_LIFE_TIME_IN_MINUTES);
			connection.addSubscription(streamingSubscription);

			// Capture and call webhook URL for each new notification
			connection.addOnNotificationEvent(new StreamingSubscriptionConnection.INotificationEventDelegate() {
				@Override
				public void notificationEventDelegate(Object sender, NotificationEventArgs args) {
					for (NotificationEvent notificationEvent : args.getEvents()) {
						// Get id of the notification
						ItemId eventId = ((ItemEvent) notificationEvent).getItemId();

						// Set response to be sent on calling the webhook URL
						SubscribeNotificationResponse subscribeResponse = new SubscribeNotificationResponse(
								eventId.toString(), notificationEvent.getEventType().toString(),
								streamingSubscription.getId().toString());

						// Call webhook for each new notification
						callWebhook(subscribe.getWebhookNotificationUrl(), subscribeResponse);
					}
				}
			});

			connection.addOnDisconnect(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
				@Override
				public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
					StreamingSubscriptionConnection connection = (StreamingSubscriptionConnection) sender;
					try {
						connection.open();
					} catch (Throwable e) {
						throw new InternalServerException(new ApiResponse(Boolean.FALSE,
								"error occurred while renewing subscription. Error: " + e.getMessage()));
					}
				}
			});

			connection.open();

			Subscription subscription = new Subscription(streamingSubscription.getId().toString());
			return new ResponseEntity<Subscription>(subscription, HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while creating subscription. Error: " + e.getMessage()));
		}
	}

	public void callWebhook(String notificationUrl, SubscribeNotificationResponse sub) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SubscribeNotificationResponse> requestEntity = new HttpEntity<>(sub, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForLocation(notificationUrl, requestEntity, SubscribeNotificationResponse.class);
		return;
	}
}
