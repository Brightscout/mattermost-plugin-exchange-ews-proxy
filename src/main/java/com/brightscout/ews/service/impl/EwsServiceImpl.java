package com.brightscout.ews.service.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.EwsService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;

@Service
public class EwsServiceImpl implements EwsService {

	private ExchangeService service;

	public EwsServiceImpl(@Value("${app.username}") String username, @Value("${app.password}") String password,
			@Value("${app.domain}") String domain, @Value("${app.exchangeServerURL}") String exchangeServerUrl) {
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials(username, password, domain);
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI(exchangeServerUrl));
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while instantiating exchange service. Error: " + e.getMessage()));
		}
	}

	@Override
	public ExchangeService impersonateUser(String userEmail) {
		ImpersonatedUserId impersonatedUserId = new ImpersonatedUserId(ConnectingIdType.SmtpAddress, userEmail);
		service.setImpersonatedUserId(impersonatedUserId);
		return service;
	}

	public ExchangeService getService() {
		return service;
	}

	public void setService(ExchangeService service) {
		this.service = service;
	}
}
