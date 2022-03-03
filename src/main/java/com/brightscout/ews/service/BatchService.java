package com.brightscout.ews.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.CalendarViewBatchRequest;
import com.brightscout.ews.model.CalendarViewBatchResponse;
import com.brightscout.ews.model.UserBatchSingleResponse;

public interface BatchService {

	public ResponseEntity<CalendarViewBatchResponse> getEvents(CalendarViewBatchRequest requests) throws InternalServerException;

	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(List<String> emails) throws InternalServerException;
}