package com.ews.ews.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.model.UserBatchSingleResponse;

public interface BatchService {

	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws Exception;
	
	public ResponseEntity<ArrayList<UserBatchSingleResponse>> getUsers(ArrayList<String> emails) throws Exception;

}
