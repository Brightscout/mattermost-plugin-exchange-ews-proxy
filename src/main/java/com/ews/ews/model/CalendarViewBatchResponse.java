package com.ews.ews.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewBatchResponse {

	private ArrayList<CalendarViewSingleResponse> responses;

	public CalendarViewBatchResponse(ArrayList<CalendarViewSingleResponse> responses) {
		this.responses = responses;
	}

}
