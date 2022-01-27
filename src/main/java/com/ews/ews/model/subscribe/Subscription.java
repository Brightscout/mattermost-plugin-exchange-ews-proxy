package com.ews.ews.model.subscribe;

public class Subscription {
	private String subscriptionId;
	private String webhookNotificationUrl;

	public Subscription() {
	}

	public Subscription(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public void setWebhookNotificationUrl(String webhookNotificationUrl) {
		this.webhookNotificationUrl = webhookNotificationUrl;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public String getWebhookNotificationUrl() {
		return webhookNotificationUrl;
	}
}
