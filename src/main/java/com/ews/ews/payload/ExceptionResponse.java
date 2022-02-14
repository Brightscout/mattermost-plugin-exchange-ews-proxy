package com.ews.ews.payload;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
	private String error;
	private Integer status;
	private List<String> messages;
	private Instant timestamp;

	public ExceptionResponse(List<String> messages, String error, Integer status) {
		setMessages(messages);
		this.error = error;
		this.status = status;
		this.timestamp = Instant.now();
	}

	public final void setMessages(List<String> messages) {
		if (messages == null) {
			this.messages = new ArrayList<>();
		} else {
			this.messages = Collections.unmodifiableList(messages);
		}
	}
}
