package com.brightscout.ews.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserBatchRequest {

	private List<String> emails;

	public UserBatchRequest(@NonNull List<String> emails) {
		this.emails = emails;
	}
}
