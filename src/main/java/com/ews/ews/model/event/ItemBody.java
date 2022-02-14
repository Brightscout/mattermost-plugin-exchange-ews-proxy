package com.ews.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ItemBody {

	private String content; // Text

	private String contentType; // BodyType

	public ItemBody() {
	}

	public ItemBody(@NonNull String content, @NonNull String contentType) {
		this.content = content;
		this.contentType = contentType;
	}
}
