package com.ews.ews.model;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
