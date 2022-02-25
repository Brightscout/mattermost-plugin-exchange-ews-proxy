package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailAddress {

	@NonNull
	private String address;

	@NonNull
	private String name;
}
