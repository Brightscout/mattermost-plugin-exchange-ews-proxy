package com.ews.ews.service;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.User;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface UserService {
	public ResponseEntity<User> getUser(ExchangeService service, String email) throws Exception;
}
