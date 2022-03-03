package com.brightscout.ews.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscribe.SubscribeNotificationResponse;
import com.brightscout.ews.model.subscribe.Subscription;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.SubscriptionService;
import com.brightscout.ews.utils.AppConstants;

import lombok.Getter;
import lombok.Setter;
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

	@Getter
	@Setter
	static class Pair {
		private StreamingSubscription streamingSubscription;
		private StreamingSubscriptionConnection streamingSubscriptionConnection;

		public Pair(StreamingSubscription streamingSubscription,
				StreamingSubscriptionConnection streamingSubscriptionConnection) {
			this.streamingSubscription = streamingSubscription;
			this.streamingSubscriptionConnection = streamingSubscriptionConnection;
		}
	}

	private static final Map<String, Pair> SUBSCRIPTION_ID_PAIR_MAP = new HashMap<>();

	@Override
	public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe)
			throws Exception {
		try {
			// Get existing subscription if any
			Pair existingSubscription = SUBSCRIPTION_ID_PAIR_MAP.get(subscribe.getSubscriptionId());
			if (existingSubscription != null) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "error occurred while creating subscription. Error: "
								+ "Subscription already exists for subscriptionId: "
								+ subscribe.getSubscriptionId()));
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

			// Create a streaming connection to the service object, over which events are
			// returned to the client.
			// Keep the streaming connection open for 30 minutes.
			final StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service,
					AppConstants.SUBSCRIPTION_LIFETIME_IN_MINUTES);

			// Store the subscription and subscription connection in map, used during unsubscribe
			SUBSCRIPTION_ID_PAIR_MAP.put(streamingSubscription.getId(), new Pair(streamingSubscription, connection));

			// Add streaming subscription to the connection
			connection.addSubscription(streamingSubscription);

			// Throw exception on any subscription error
			connection.addOnSubscriptionError(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
				@Override
				public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
					if (args.getException() != null) {
						throw new InternalServerException(new ApiResponse(Boolean.FALSE,
								"error occurred in the subscription. Error: " + args.getException().getMessage()));
					}
				}
			});

			// Capture and call webhook URL for each new notifications
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

			// Open the subscription connection when the previous subscription duration is
			// expired
			connection.addOnDisconnect(new StreamingSubscriptionConnection.ISubscriptionErrorDelegate() {
				@Override
				public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
					StreamingSubscriptionConnection connection = (StreamingSubscriptionConnection) sender;
					try {
						Pair pair = SUBSCRIPTION_ID_PAIR_MAP.get(subscribe.getSubscriptionId());
						if (pair != null && pair.streamingSubscriptionConnection != null) {
							connection.open();
						}
					} catch (Exception e) {
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
			Pair pair = SUBSCRIPTION_ID_PAIR_MAP.get(subscribe.getSubscriptionId());
			StreamingSubscriptionConnection subscriptionConnection = pair.streamingSubscriptionConnection;
			StreamingSubscription subscription = pair.streamingSubscription;

			// remove the subscription from map
			SUBSCRIPTION_ID_PAIR_MAP.remove(subscribe.getSubscriptionId());

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
										new ApiResponse(Boolean.FALSE,
												"error occurred while unsubscribing. Error: "
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
