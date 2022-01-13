package com.ews.ews.model;

public class CalendarViewBatchRequest {

	private CalendarViewSingleRequest[] requests;

	public CalendarViewSingleRequest[] getRequests() {
		return requests;
	}

	public void setRequests(CalendarViewSingleRequest[] requests) {
		this.requests = requests;
	}

}
