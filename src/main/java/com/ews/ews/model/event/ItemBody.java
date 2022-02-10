package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBody {

	private String content; // Text

	private String contentType; // BodyType

	public ItemBody() {
	}

	public ItemBody(String content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

}
