package com.ews.ews.service.impl;
import com.ews.ews.service.SubscriptionService;

import java.util.ArrayList;
import java.util.List;
import com.ews.ews.utils.AppConstants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

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

import com.ews.ews.model.subscribe.Subscribe;
import com.ews.ews.model.subscribe.SubscribeNotificationResponse;
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    public ResponseEntity<Subscribe> subscribeToStreamNotifications(ExchangeService service, Subscribe subscribe) throws Exception {
        try {
            List<FolderId> folderId = new ArrayList<FolderId>();
            folderId.add(new FolderId(WellKnownFolderName.Calendar));
            
            final StreamingSubscription streamingSubscription = service.subscribeToStreamingNotifications(
                folderId,
                EventType.Created
                // TODO: confirm all required types
                // EventType.Deleted,
                // EventType.Modified
            );
            
            // Create a streaming connection to the service object, over which events are returned to the client.
            // Keep the streaming connection open for 30 minutes.
            // TODO: add logic to close the connection
            final StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service, AppConstants.SUBSCRIPTION_LIFE_TIME_IN_MINUTE);
            connection.addSubscription(streamingSubscription);
            
            // Capture and call webhook URL for each new notifications
            connection.addOnNotificationEvent(new StreamingSubscriptionConnection.INotificationEventDelegate() {
                @Override
                public void notificationEventDelegate(Object sender, NotificationEventArgs args) {
                    for (NotificationEvent notification : args.getEvents()){
                        // Get id of the notification
                        ItemId eventId = ((ItemEvent)notification).getItemId();
                    
                        // Set response to be sent on calling the webhook URL
                        SubscribeNotificationResponse subscribeResponse = new SubscribeNotificationResponse(eventId.toString() , notification.getEventType().toString(), streamingSubscription.getId().toString());
                    
                        // Call webhook for each new notifications
                        callWebhook(subscribe.getWebhookNotificationUrl(), subscribeResponse);
                    }
                }
            });

            connection.open();
          
            Subscribe subscription = new Subscribe(streamingSubscription.getId().toString());
            return new ResponseEntity<Subscribe>(subscription, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Subscription error");
        }
    }

    public void callWebhook(String notificationUrl, SubscribeNotificationResponse sub) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubscribeNotificationResponse> requestEntity = new HttpEntity<>(sub, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(notificationUrl, requestEntity, SubscribeNotificationResponse.class);
        return;
    }
}
