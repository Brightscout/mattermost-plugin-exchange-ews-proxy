package com.brightscout.ews.service.impl;

import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.TimeZoneService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;

@Service
public class TimeZoneServiceImpl implements TimeZoneService {

	Logger logger = LoggerFactory.getLogger(TimeZoneServiceImpl.class);

	@Override
	public ResponseEntity<String> getTimeZone(ExchangeService service, String userEmail)
			throws InternalServerException {
		logger.debug("Getting timezone for user: {}", userEmail);
		try {
			GetUserAvailabilityResults userAvailability = service.getUserAvailability(
					Collections.singletonList(new AttendeeInfo(userEmail)),
					new TimeWindow(new Date(), new Date(System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY)),
					AvailabilityData.FreeBusy);
			AttendeeAvailability attendeeAvailability = userAvailability.getAttendeesAvailability()
					.getResponseAtIndex(0);
			String timezone = attendeeAvailability.getWorkingHours().getTimeZone().toString();

			return new ResponseEntity<>(timezone, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(
					new ApiResponse(Boolean.FALSE, "error occurred while fetching timezone. Error: " + e.getMessage()));
		}
	}
}
