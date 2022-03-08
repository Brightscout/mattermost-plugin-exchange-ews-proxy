package com.brightscout.ews.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.CalendarViewBatchRequest;
import com.brightscout.ews.model.CalendarViewBatchResponse;
import com.brightscout.ews.model.UserBatchSingleResponse;
import com.brightscout.ews.model.subscribe.SubscriptionBatchSingleRequest;
import com.brightscout.ews.model.subscribe.SubscriptionBatchSingleResponse;
import com.brightscout.ews.service.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	private BatchService batchService;

	@Autowired
	public BatchController(BatchService batchService) {
		this.batchService = batchService;
	}

	@PostMapping({ "/event" })
	public ResponseEntity<CalendarViewBatchResponse> getEvents(@Valid @RequestBody CalendarViewBatchRequest requests)
			throws InternalServerException {
		return batchService.getEvents(requests);
	}

	@PostMapping({ "/user" })
	public ResponseEntity<List<UserBatchSingleResponse>> getUsers(@Valid @RequestBody List<String> emails)
			throws InternalServerException {
		return batchService.getUsers(emails);
	}
	
	@PostMapping({ "/subscribe" })
	public ResponseEntity<List<SubscriptionBatchSingleResponse>> getSubscriptions(@Valid @RequestBody List<SubscriptionBatchSingleRequest> requests)
			throws InternalServerException {
		return batchService.getSubscriptions(requests);
	}
}
