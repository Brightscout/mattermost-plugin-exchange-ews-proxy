package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DateTime {

	@NonNull
	private String date;

	@NonNull
	private String timeZone;
}
