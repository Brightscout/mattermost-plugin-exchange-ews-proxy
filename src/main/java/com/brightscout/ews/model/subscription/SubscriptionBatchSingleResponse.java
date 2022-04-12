package com.brightscout.ews.model.subscription;

import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SubscriptionBatchSingleResponse {

	@NonNull
	private String email;

	@NonNull
	private Subscription subscription;

	private ApiResponse error;
	
	public SubscriptionBatchSingleResponse(String email, ApiResponse error) {
		this.email = email;
		this.error = error;
	}
}
