package com.ews.ews.service.impl;

import java.net.URI;

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

	public EWSServiceImpl() {
		try {
			this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
			// TODO: Fetch credentials from environment variables
			String userName = "ServiceAccount";
			String password = "ubL.qJIuNv5kUNAgdmnL.6l@VKY%J*gQ";
			String domain = "brightscout";
			ExchangeCredentials credentials = new WebCredentials(userName, password, domain);
			this.service.setCredentials(credentials);
			this.service.setTraceEnabled(true);
			// TODO: Fetch exchange server URL from environment variables
			String exchangeURL = "https://exchangenode1.ad.brightscout.com/ews/exchange.asmx";
			this.service.setUrl(new URI(exchangeURL));
		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while instantiating exchange service. Error: " + e.getMessage()));
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
