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

    Subscription subscription;

    @BeforeEach
    public void setUp() {
        subscription = new Subscription(TestUtils.ID);
    }

    @Test
    public void subscribeToPushNotificationsSuccess() {
        ResponseEntity<Subscription> subscriptionResponse = new ResponseEntity<Subscription>(subscription, HttpStatus.OK);

        Mockito.when(subscriptionService.subscribeToPushNotifications(ewsService.impersonateUser(TestUtils.EMAIL), subscription)).thenReturn(subscriptionResponse);

        ResponseEntity<Subscription> subscriptionResult = subscriptionController.subscribeToPushNotifications(TestUtils.EMAIL, subscription);
        Assertions.assertTrue(subscriptionResult.equals(subscriptionResponse));
    }

    @Test
    public void subscribeToPushNotificationsFailed() {
        Mockito.when(subscriptionService.subscribeToPushNotifications(ewsService.impersonateUser(TestUtils.EMAIL), subscription)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> subscriptionController.subscribeToPushNotifications(TestUtils.EMAIL, subscription));
    }
}
