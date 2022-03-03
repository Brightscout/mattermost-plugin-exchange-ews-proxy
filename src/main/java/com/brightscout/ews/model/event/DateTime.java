package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DateTime {

	private String date;

	private String timeZone;

	public DateTime() {
	}

	public DateTime(@NonNull String date, @NonNull String timeZone) {
		this.date = date;
		this.timeZone = timeZone;
	}
}
