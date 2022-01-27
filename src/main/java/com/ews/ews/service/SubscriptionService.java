package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import microsoft.exchange.webservices.data.core.ExchangeService;

import com.ews.ews.model.subscribe.Subscription;

public interface SubscriptionService {
    public ResponseEntity<Subscription> subscribeToStreamNotifications(ExchangeService service, Subscription subscribe) throws Exception;
}
