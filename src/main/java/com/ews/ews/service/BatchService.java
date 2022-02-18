package com.ews.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.model.UserBatchSingleResponse;

public interface BatchService {

	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws Exception;

	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(List<String> emails) throws Exception;
}
