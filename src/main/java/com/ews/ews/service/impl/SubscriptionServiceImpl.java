package com.ews.ews.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
	private static final ConcurrentHashMap<String, StreamingSubscriptionConnection> subscriptionConnectionMap = new ConcurrentHashMap<String, StreamingSubscriptionConnection>();
	private static final ConcurrentHashMap<String, StreamingSubscription> subscriptionMap = new ConcurrentHashMap<String, StreamingSubscription>();

	@Override
	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws Exception {
		try {
			// Get existing subscription if any
			StreamingSubscription existingSubscription = subscriptionMap.get(subscribe.getSubscriptionId());
			if (existingSubscription != null) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "error occurred while creating subscription. Error: "
								+ "Subscription already exists for subscriptionId: " + subscribe.getSubscriptionId()));
			}
			// Get all folderIds of the user
			List<FolderId> folderIds = new ArrayList<FolderId>();
			folderIds.add(new FolderId(WellKnownFolderName.Calendar));

			// Subscribe to stream notification service
			final StreamingSubscription streamingSubscription = service.subscribeToStreamingNotifications(folderIds,
					EventType.Created
			// TODO: confirm all required types
			// EventType.Deleted,
			// EventType.Modified
			);

			// Store the subscription in map, used during unsubscribe
			subscriptionMap.put(streamingSubscription.getId(), streamingSubscription);

			// Get existing subscription connection if any
			StreamingSubscriptionConnection existingSubscriptionConnection = subscriptionConnectionMap
					.get(subscribe.getSubscriptionId());

			if (existingSubscriptionConnection != null) {
				throw new InternalServerException(new ApiResponse(Boolean.FALSE,
						"error occurred while creating subscription. Error: "
								+ "Subscription connection already exists for subscriptionId: "
								+ subscribe.getSubscriptionId()));
			}
			// Create a streaming connection to the service object, over which events are
			// returned to the client.
			// Keep the streaming connection open for 30 minutes.
			final StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service,
					AppConstants.SUBSCRIPTION_LIFE_TIME_IN_MINUTES);

			// Store the subscription connection in map, used during unsubscribe
			subscriptionConnectionMap.put(streamingSubscription.getId(), connection);

			// Add streaming subscription to the connection
			connection.addSubscription(streamingSubscription);

			// Throw exception on any subscription error
			connection.addOnSubscriptionError(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
				@Override
				public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
					if (args.getException() != null) {
						throw new InternalServerException(
								new ApiResponse(Boolean.FALSE, "error occurred in the subscription. Error: "
										+ args.getException().getMessage()));
					}
				}
			});

			// Capture and call webhook URL for each new notifications
			connection.addOnNotificationEvent(new StreamingSubscriptionConnection.INotificationEventDelegate() {
				@Override
				public void notificationEventDelegate(Object sender, NotificationEventArgs args) {
					for (NotificationEvent notification : args.getEvents()) {
						// Get id of the notification
						ItemId eventId = ((ItemEvent) notification).getItemId();

						// Set response to be sent on calling the webhook URL
						SubscribeNotificationResponse subscribeResponse = new SubscribeNotificationResponse(
								eventId.toString(), notification.getEventType().toString(),
								streamingSubscription.getId().toString());

						// Call webhook for each new notifications
						callWebhook(subscribe.getWebhookNotificationUrl(), subscribeResponse);
					}
				}
			});

			// Open the subscription connection when the previous subscription duration is
			// expired
			connection.addOnDisconnect(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
				@Override
				public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
					StreamingSubscriptionConnection connection = (StreamingSubscriptionConnection) sender;
					try {
						StreamingSubscriptionConnection subscriptionConnection = subscriptionConnectionMap
								.get(subscribe.getSubscriptionId());
						if (subscriptionConnection != null) {
							connection.open();
						}
					} catch (Throwable e) {
						throw new InternalServerException(new ApiResponse(Boolean.FALSE,
								"error occurred while renewing subscription. Error: " + e.getMessage()));
					}
				}
			});

			// Open the streaming connection when subscribed
			connection.open();

			// Set and send response on successful subscription
			Subscription subscription = new Subscription(streamingSubscription.getId().toString());
			return new ResponseEntity<Subscription>(subscription, HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while creating subscription. Error: " + e.getMessage()));
		}
	}

	// Used to make a post type call on the provided URL with captured notification data
	public void callWebhook(String notificationUrl, SubscribeNotificationResponse sub) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SubscribeNotificationResponse> requestEntity = new HttpEntity<>(sub, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForLocation(notificationUrl, requestEntity, SubscribeNotificationResponse.class);
		return;
	}

	// Unsubscribe to the streaming notification service
	@Override
	public ResponseEntity<String> unsubscribeToStreamNotifications(Subscription subscribe) throws Exception {
		try {
			// get the connection from map
			StreamingSubscriptionConnection subscriptionConnection = subscriptionConnectionMap
					.get(subscribe.getSubscriptionId());

			// remove the connection from map
			subscriptionConnectionMap.remove(subscribe.getSubscriptionId());

			// get the subscription from map
			StreamingSubscription subscription = subscriptionMap.get(subscribe.getSubscriptionId());

			// remove the subscription from map
			subscriptionMap.remove(subscribe.getSubscriptionId());

			// unsubscribe to streaming notification subscription
			subscription.unsubscribe();

			// close the streaming notification connection
			subscriptionConnection.close();

			// remove streaming notification subscription from the connection
			subscriptionConnection.removeSubscription(subscription);

			// Throw exception on any error on removal of subscription
			subscriptionConnection
					.removeSubscriptionError(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
						@Override
						public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
							if (args.getException() != null) {
								throw new InternalServerException(
										new ApiResponse(Boolean.FALSE, "error occurred while unsubscribing. Error: "
												+ args.getException().getMessage()));
							}
						}
					});
			return new ResponseEntity<String>("Unsubscribed", HttpStatus.OK);
		} catch (Exception e) {
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while unsubscribing. Error: " + e.getMessage()));
		}
	}
}
