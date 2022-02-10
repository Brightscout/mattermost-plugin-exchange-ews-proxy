package com.ews.ews.model.subscribe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subscription {
	private String subscriptionId;
	private String webhookNotificationUrl;

	public Subscription() {
	}

	public Subscription(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}
