package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.CalendarViewBatchRequest;
import com.ews.ews.model.CalendarViewBatchResponse;
import com.ews.ews.service.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	@Autowired
	BatchService batchService;
	
	@PostMapping({"/event"})
	public ResponseEntity<CalendarViewBatchResponse> getEvents(@RequestBody CalendarViewBatchRequest requests) throws Exception {
		return this.batchService.getEvents(requests);
	}
}
