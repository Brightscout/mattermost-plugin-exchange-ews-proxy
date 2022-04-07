package com.ews.ews;

import com.brightscout.ews.controller.SubscriptionController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.subscription.Subscription;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.SubscriptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class SubscriptionTests {
    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private EwsService ewsService;

    @Mock
    private SubscriptionService subscriptionService;

    // Dummy data
    String subscriptionId = "TWFueSBoYW5kcyBtYWtlIGxpZ2h0IHdvcmsu";
    String email = "test-user@ad.brightscout.com";

    Subscription subscription;

    @BeforeEach
    public void setUp() {
        subscription = new Subscription(subscriptionId);
    }

    @Test
    public void subscribeToPushNotificationsSuccess() {
        ResponseEntity<Subscription> subscriptionResponse = new ResponseEntity<Subscription>(subscription, HttpStatus.OK);

        Mockito.when(subscriptionService.subscribeToPushNotifications(ewsService.impersonateUser(email), subscription)).thenReturn(subscriptionResponse);

        ResponseEntity<Subscription> subscriptionResult = subscriptionController.subscribeToPushNotifications(email,subscription);
        Assertions.assertEquals(HttpStatus.OK, subscriptionResult.getStatusCode());
        Assertions.assertTrue(subscriptionResult.equals(subscriptionResponse));
    }

    @Test
    public void subscribeToPushNotificationsFailed() {
        Mockito.when(subscriptionService.subscribeToPushNotifications(ewsService.impersonateUser(email), subscription)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> subscriptionController.subscribeToPushNotifications(email,subscription));
    }
}
