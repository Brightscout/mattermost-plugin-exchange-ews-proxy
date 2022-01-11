package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import microsoft.exchange.webservices.data.core.ExchangeService;

import com.ews.ews.model.User;

public interface UserService {
    public ResponseEntity<User> getUser(ExchangeService service, String email) throws Exception;
}
