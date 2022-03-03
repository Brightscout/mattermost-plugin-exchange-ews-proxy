package com.ews.ews.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.model.UserBatchSingleResponse;
import com.ews.ews.service.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	private BatchService batchService;

	public BatchController(BatchService batchService) {
		this.batchService = batchService;
	}

	@PostMapping({ "/event" })
	public ResponseEntity<CalendarViewBatchResponse> getEvents(@Valid @RequestBody CalendarViewBatchRequest requests)
			throws Exception {
		return batchService.getEvents(requests);
	}

	@PostMapping({ "/user" })
	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(@Valid @RequestBody List<String> emails)
			throws Exception {
		return batchService.getUsers(emails);
	}
}
