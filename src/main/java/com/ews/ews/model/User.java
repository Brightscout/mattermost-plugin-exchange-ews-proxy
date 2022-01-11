package com.ews.ews.model;

public class User {
	
	private String id;
	
	private String displayName;
	
	private String userPrincipalName;
	
	private String mail;

	public User() {
	}

	public User(String id, String displayName, 	String userPrincipalName, String mail) {
		this.id = id;
		this.displayName = displayName;
		this.userPrincipalName = userPrincipalName;
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

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
