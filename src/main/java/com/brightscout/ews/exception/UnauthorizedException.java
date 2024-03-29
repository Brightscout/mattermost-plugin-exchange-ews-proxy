package com.brightscout.ews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.brightscout.ews.payload.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -5873503544913663596L;

	private ApiResponse apiResponse;

	private String message;

	public UnauthorizedException(ApiResponse apiResponse) {
		super(apiResponse.getMessage());
		this.apiResponse = apiResponse;
	}

	public UnauthorizedException(String message) {
		super(message);
		this.message = message;
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}
}
