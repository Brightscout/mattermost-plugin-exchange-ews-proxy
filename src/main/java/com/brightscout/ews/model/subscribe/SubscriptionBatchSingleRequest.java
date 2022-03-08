package com.brightscout.ews.model.subscribe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionBatchSingleRequest {

	private String email;

	private Subscription subscription;
}
