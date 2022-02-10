package com.ews.ews.model;

import java.util.ArrayList;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewBatchRequest {

	private ArrayList<CalendarViewSingleRequest> requests;

}
