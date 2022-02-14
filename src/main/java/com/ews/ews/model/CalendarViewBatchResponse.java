package com.ews.ews.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewBatchResponse {

	private List<CalendarViewSingleResponse> responses;

	public CalendarViewBatchResponse(@NonNull List<CalendarViewSingleResponse> responses) {
		this.responses = responses;
	}
}
