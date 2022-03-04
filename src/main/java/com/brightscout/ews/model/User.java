package com.brightscout.ews.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

	@NonNull
	private String id;

	@NonNull
	private String displayName;

	@NonNull
	private String mail;

	public User(String mail) {
		this.mail = mail;
	}
}
