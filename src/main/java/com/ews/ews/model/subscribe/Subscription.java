package com.ews.ews.model.subscribe;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Subscription {
	private String subscriptionId;
	private String webhookNotificationUrl;

	public Subscription() {
	}

	public Subscription(@NonNull String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
}
