package com.ews.ews.service;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface EWSService {
	
	public ExchangeService impersonateUser(String userEmail);
	
}
