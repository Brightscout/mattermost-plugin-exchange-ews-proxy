package com.brightscout.ews.model.subscribe;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Subscription {
	
	@NonNull
	private String subscriptionId;

	private String webhookNotificationUrl;
}
