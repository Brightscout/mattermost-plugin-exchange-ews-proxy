package com.ews.ews.model.event;

public class ItemBody {
	
	private String content; // Text
	
	private String contentType; // BodyType
	
	public ItemBody() {
	}
	
	public ItemBody(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
