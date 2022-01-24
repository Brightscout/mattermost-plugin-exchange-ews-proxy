package com.ews.ews.model;

import java.util.ArrayList;

public class CalendarViewBatchResponse {

	private ArrayList<CalendarViewSingleResponse> responses;

	public CalendarViewBatchResponse(ArrayList<CalendarViewSingleResponse> responses) {
		this.responses = responses;
	}

	public ArrayList<CalendarViewSingleResponse> getResponses() {
		return responses;
	}

	public void setResponses(ArrayList<CalendarViewSingleResponse> responses) {
		this.responses = responses;
	}

}
