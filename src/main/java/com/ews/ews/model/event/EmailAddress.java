package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAddress {

	private String address;

	private String name;

	public EmailAddress() {
	}

	public EmailAddress(String address, String name) {
		this.address = address;
		this.name = name;
	}

}
