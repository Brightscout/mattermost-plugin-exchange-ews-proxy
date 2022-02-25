package com.brightscout.ews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 8826720095015192971L;
	private ApiResponse apiResponse;

	public InternalServerException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
