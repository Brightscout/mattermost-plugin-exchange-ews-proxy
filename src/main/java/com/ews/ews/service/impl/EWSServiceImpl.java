package com.ews.ews.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ews.ews.service.EWSService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;

@Service
public class EWSServiceImpl implements EWSService {

	private ExchangeService service;

	public EWSServiceImpl(@Value("${app.username}") String username, @Value("${app.password}") String password,
			@Value("${app.domain}") String domain, @Value("${app.exchangeServerURL}") String exchangeServerURL) {
		System.out.println("Initialising EWS service with service account credentials");
		this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

		ExchangeCredentials credentials = new WebCredentials(username, password, domain);
		this.service.setCredentials(credentials);
		this.service.setTraceEnabled(true);
		try {
			this.service.setUrl(new URI(exchangeServerURL));
		} catch (Exception e) {
			System.out.println("Failed to discover URL");
			e.printStackTrace();
		}
	}

	@Override
	public ExchangeService impersonateUser(String userEmail) {
		ImpersonatedUserId impersonatedUserId = new ImpersonatedUserId(ConnectingIdType.SmtpAddress, userEmail);
		this.service.setImpersonatedUserId(impersonatedUserId);
		return this.service;
	}

	public ExchangeService getService() {
		return service;
	}

	public void setService(ExchangeService service) {
		this.service = service;
	}

}
