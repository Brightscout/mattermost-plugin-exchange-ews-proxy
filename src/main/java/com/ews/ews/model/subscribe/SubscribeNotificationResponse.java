package com.ews.ews.model.subscribe;

public class SubscribeNotificationResponse {
	private String eventId;
	private String changeType;
	private String subscriptionId;

	public SubscribeNotificationResponse() {
	}

	public SubscribeNotificationResponse(String eventId, String changeType, String subscriptionId) {
		this.eventId = eventId;
		this.changeType = changeType;
		this.subscriptionId = subscriptionId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getEventId() {
		return eventId;
	}

	public String getChangeType() {
		return changeType;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}
}
