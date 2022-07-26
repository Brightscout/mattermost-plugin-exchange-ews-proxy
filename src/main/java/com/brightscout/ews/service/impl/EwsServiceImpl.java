package com.brightscout.ews.service.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;

@Service
public class EwsServiceImpl implements EwsService {

	private String username;

	private String password;

	private String domain;

	private String exchangeServerUrl;

	Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	public EwsServiceImpl(@Value("${app.username}") String username, @Value("${app.password}") String password,
			@Value("${app.domain}") String domain, @Value("${app.exchangeServerURL}") String exchangeServerUrl) {
		this.username = username;
		this.password = password;
		this.domain = domain;
		this.exchangeServerUrl = exchangeServerUrl;
	}

	@Override
	public ExchangeService impersonateUser(String userEmail) {
		logger.debug("Impersonating User for user: {}", userEmail);
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials(username, password, domain);
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI(String.format("%s/%s", exchangeServerUrl, AppConstants.EXCHANGE_SERVER_ADDRESS)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while instantiating exchange service. Error: " + e.getMessage()));
		}
		ImpersonatedUserId impersonatedUserId = new ImpersonatedUserId(ConnectingIdType.SmtpAddress, userEmail);
		service.setImpersonatedUserId(impersonatedUserId);
		return service;
	}
}
