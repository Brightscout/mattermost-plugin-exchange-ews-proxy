package com.ews.ews.model;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewBatchRequest {

	@NotNull
	private List<CalendarViewSingleRequest> requests;
}
