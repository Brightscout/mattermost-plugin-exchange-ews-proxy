package com.ews.ews.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private String id;

	private String displayName;

	private String mail;

	public User() {
	}

	public User(String mail) {
		this.mail = mail;
	}

	public User(String id, String displayName, String mail) {
		this.id = id;
		this.displayName = displayName;
		this.mail = mail;
	}

}
