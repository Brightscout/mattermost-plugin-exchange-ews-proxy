package com.brightscout.ews.model.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionBatchSingleRequest {

	private String email;

	private Subscription subscription;
}
