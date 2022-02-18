package com.ews.ews.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class User {

	private String id;

	private String displayName;

	private String mail;

	public User() {
	}

	public User(@NonNull String mail) {
		this.mail = mail;
	}

	public User(@NonNull String id, @NonNull String displayName, @NonNull String mail) {
		this.id = id;
		this.displayName = displayName;
		this.mail = mail;
	}
}
