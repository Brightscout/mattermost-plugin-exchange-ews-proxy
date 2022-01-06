package com.ews.ews.model.event;

public class DateTime {
	
	private String dateTime;
	
	private String timeZone;
	
	public DateTime() {
	}
	
	public DateTime(String dateTime, String timeZone) {
		this.dateTime = dateTime;
		this.timeZone = timeZone;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}
