package com.brightscout.ews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brightscout.ews.utils.AppConstants;

@Service
public class RestService {

    private final RestTemplate restTemplate;
    
    private String mattermostPluginBaseUrl;

    @Autowired
    public RestService(RestTemplateBuilder restTemplateBuilder, @Value("${app.mattermostPluginBaseUrl}") String mattermostPluginBaseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.mattermostPluginBaseUrl = mattermostPluginBaseUrl;
    }

    public void syncSubscriptions() {
        String url = String.format("%s%s", mattermostPluginBaseUrl, AppConstants.SYNC_SUBSCRIPTIONS_URL);
        // send sync subscription request
        restTemplate.getForEntity(url, Void.class);
    }
}
