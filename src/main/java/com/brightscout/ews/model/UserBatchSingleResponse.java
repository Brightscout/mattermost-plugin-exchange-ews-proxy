package com.brightscout.ews.model;

import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserBatchSingleResponse {

	@NonNull
	private User user;

	@NonNull
	private ApiResponse error;

	public UserBatchSingleResponse(@NonNull User user) {
		this.user = user;
	}
}
