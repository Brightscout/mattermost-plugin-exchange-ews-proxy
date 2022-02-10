package com.ews.ews.service.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ews.ews.exception.InternalServerException;
import com.ews.ews.payload.ApiResponse;
import com.ews.ews.service.EWSService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;

@Service
public class EWSServiceImpl implements EWSService {

	private ExchangeService service;

	public EWSServiceImpl(@Value("${app.username}") String username, @Value("${app.password}") String password,
			@Value("${app.domain}") String domain, @Value("${app.exchangeServerURL}") String exchangeServerURL) {
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials(username, password, domain);
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI(exchangeServerURL));
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
