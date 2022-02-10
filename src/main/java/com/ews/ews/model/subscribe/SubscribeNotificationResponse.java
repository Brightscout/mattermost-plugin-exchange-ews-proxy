package com.ews.ews.model.subscribe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
