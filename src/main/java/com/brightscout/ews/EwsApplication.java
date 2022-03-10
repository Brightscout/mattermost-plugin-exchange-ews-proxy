package com.brightscout.ews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.EnableRetry;

import com.brightscout.ews.service.RestService;

@SpringBootApplication
@EnableRetry
public class EwsApplication {

	private RestService restService;

	@Autowired
	public EwsApplication(RestService restService) {
		this.restService = restService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		this.restService.syncSubscriptions();
	}

	public static void main(String[] args) {
		SpringApplication.run(EwsApplication.class, args);
	}
}
