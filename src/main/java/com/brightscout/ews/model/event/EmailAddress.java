package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EmailAddress {

	private String address;

	private String name;

	public EmailAddress() {
	}

	public EmailAddress(@NonNull String address, @NonNull String name) {
		this.address = address;
		this.name = name;
	}
}
