package com.ews.ews.model;

import com.ews.ews.payload.ApiResponse;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ApiResponse getError() {
		return error;
	}

	public void setError(ApiResponse error) {
		this.error = error;
	}

}
