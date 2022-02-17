package com.ews.ews.model;

import java.util.ArrayList;

public class UserBatchRequest {

	private ArrayList<String> emails;
	
	public UserBatchRequest(ArrayList<String> emails) {
		this.emails = emails;
	}

	public ArrayList<String> getEmails() {
		return emails;
	}

	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}	

}
