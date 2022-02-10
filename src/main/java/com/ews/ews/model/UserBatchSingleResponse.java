package com.ews.ews.model;

import com.ews.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBatchSingleResponse {

	private User user;

	private ApiResponse error;

	public UserBatchSingleResponse(User user) {
		this.user = user;
	}

	public UserBatchSingleResponse(User user, ApiResponse error) {
		this.user = user;
		this.error = error;
	}

}
