package com.brightscout.ews.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewSingleRequest {

	private String id;

	private String startDateTime;

	private String endDateTime;
}
