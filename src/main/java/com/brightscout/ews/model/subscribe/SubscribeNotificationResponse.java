package com.brightscout.ews.model.subscribe;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SubscribeNotificationResponse {
	private String eventId;
	private String changeType;
	private String subscriptionId;

	public SubscribeNotificationResponse() {
	}

	public SubscribeNotificationResponse(@NonNull String eventId, @NonNull String changeType, @NonNull String subscriptionId) {
		this.eventId = eventId;
		this.changeType = changeType;
		this.subscriptionId = subscriptionId;
	}
}
