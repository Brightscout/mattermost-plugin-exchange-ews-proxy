package com.ews.ews.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewBatchRequest {

	private List<CalendarViewSingleRequest> requests;
}
