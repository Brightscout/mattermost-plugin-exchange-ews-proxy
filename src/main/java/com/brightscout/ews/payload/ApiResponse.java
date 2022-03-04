package com.brightscout.ews.payload;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({ "success", "message" })
public class ApiResponse implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 7702134516418120340L;

	@NonNull
	@JsonProperty("success")
	private Boolean success;

	@NonNull
	@JsonProperty("message")
	private String message;

	@NonNull
	@JsonIgnore
	private HttpStatus status;

	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
