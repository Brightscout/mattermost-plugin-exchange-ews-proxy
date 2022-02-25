package com.brightscout.ews.service;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.User;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface UserService {
	public ResponseEntity<User> getUser(ExchangeService service, String email) throws InternalServerException;
}
