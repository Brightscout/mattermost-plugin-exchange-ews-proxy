package com.ews.ews.model.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateTime {

	private String dateTime;

	private String timeZone;

	public DateTime() {
	}

	public DateTime(String dateTime, String timeZone) {
		this.dateTime = dateTime;
		this.timeZone = timeZone;
	}

}
