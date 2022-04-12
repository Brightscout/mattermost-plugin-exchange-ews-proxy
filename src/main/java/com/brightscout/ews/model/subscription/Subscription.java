package com.brightscout.ews.model.subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Subscription {
	
	@NonNull
	private String subscriptionId;

	private String webhookNotificationUrl;
}
