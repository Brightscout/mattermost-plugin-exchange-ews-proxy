package com.brightscout.ews.model.subscribe;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SubscribeNotificationResponse {

	@NonNull
	private String eventId;

	@NonNull
	private String changeType;

	@NonNull
	private String subscriptionId;
}
