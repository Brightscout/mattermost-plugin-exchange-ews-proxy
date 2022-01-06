package com.ews.ews.service.impl;

import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.service.TimeZoneService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.misc.UserConfigurationProperties;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.misc.UserConfiguration;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.time.TimeZoneDefinition;

@Service
public class TimeZoneServiceImpl implements TimeZoneService {

	@Override
	public ResponseEntity<String> getTimeZone(ExchangeService service, String userEmail) throws Exception {
		GetUserAvailabilityResults userAvailability = service.getUserAvailability(
                Collections.singletonList(new AttendeeInfo(userEmail)),
                new TimeWindow(new Date(), new Date(System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY)),
                AvailabilityData.FreeBusy);
        AttendeeAvailability attendeeAvailability = userAvailability.getAttendeesAvailability().getResponseAtIndex(0);
        String timezone = attendeeAvailability.getWorkingHours().getTimeZone().toString();
        
        return new ResponseEntity<>(timezone, HttpStatus.OK);
		
	}
	

}
