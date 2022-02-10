package com.ews.ews.exception;

import org.springframework.http.HttpStatus;

import com.ews.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EwsApiException extends RuntimeException {

	private final HttpStatus status;
	private final String message;

	public EwsApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public EwsApiException(HttpStatus status, String message, Throwable exception) {
		super(exception);
		this.status = status;
		this.message = message;
	}

}
