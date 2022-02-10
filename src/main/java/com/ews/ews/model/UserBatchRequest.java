package com.ews.ews.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBatchRequest {

	private ArrayList<String> emails;

	public UserBatchRequest(ArrayList<String> emails) {
		this.emails = emails;
	}

}
