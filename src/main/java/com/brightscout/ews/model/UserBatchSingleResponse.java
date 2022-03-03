package com.brightscout.ews.model;

import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserBatchSingleResponse {

	private User user;

	private ApiResponse error;

	public UserBatchSingleResponse(@NonNull User user) {
		this.user = user;
	}

	public UserBatchSingleResponse(@NonNull User user, @NonNull ApiResponse error) {
		this.user = user;
		this.error = error;
	}
}
