package com.ews.ews.model.subscribe;

public class Subscribe {
	private String subscriptionId;
	private String webhookNotificationUrl;

	public Subscribe() {
	}

	public Subscribe(String subscriptionId) {
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
