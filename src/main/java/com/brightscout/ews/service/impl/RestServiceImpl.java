package com.brightscout.ews.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brightscout.ews.service.RestService;
import com.brightscout.ews.utils.AppConstants;
import com.brightscout.ews.utils.AppUtils;

@Service
public class RestServiceImpl implements RestService {

	private final RestTemplate restTemplate;

	private String mattermostPluginBaseUrl;

	@Autowired
	public RestServiceImpl(RestTemplateBuilder restTemplateBuilder,
			@Value("${app.mattermostPluginBaseUrl}") String mattermostPluginBaseUrl) {
		this.restTemplate = restTemplateBuilder.build();
		this.mattermostPluginBaseUrl = mattermostPluginBaseUrl;
	}

	@Override
	public void syncSubscriptions() {
		try {
			String url = String.format("%s%s%s%s", mattermostPluginBaseUrl, AppConstants.PATH_API, AppConstants.PATH_SYNC,
					AppConstants.PATH_SUBSCRIPTION);
			// send sync subscription request
			restTemplate.getForEntity(url, Void.class);
		} catch (Exception e) {
			// TODO: Log the error
			System.out.println("error occurred while syncing subscriptions. Error: "+ e.getMessage());
		}
	}

	@Override
	public boolean getSubscriptionByID(String subscriptionID) {
		try {
			String url = String.format("%s%s%s/%s", mattermostPluginBaseUrl, AppConstants.PATH_API,
					AppConstants.PATH_SUBSCRIPTION, AppUtils.encodeBase64String(subscriptionID));
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			if (response.getStatusCode() != HttpStatus.OK) {
				// TODO: Log the error
				System.out.println("error occurred while fetching subscription by id.");
				return false;
			}
			return Boolean.parseBoolean(response.getBody());
		} catch (Exception e) {
			// TODO: Log the error
			System.out.println("error occurred while fetching subscription by id. Error: "+ e.getMessage());
			return false;
		}
	}
}
