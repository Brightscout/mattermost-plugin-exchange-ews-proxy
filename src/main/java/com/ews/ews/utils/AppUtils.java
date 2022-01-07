package com.ews.ews.utils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

public class AppUtils {
	public static String getIdFromParams(String id, HttpServletRequest request) {
		final String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
		final String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
				.toString();

		String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);

		String completeId = "";
		if (null != arguments && !arguments.isEmpty()) {
			completeId = id + '/' + arguments;
		} else {
			completeId = id;
		}

		return completeId;
	}
	
	public static Date parseDateString(String date) {
		return Date.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(date, Instant::from));
	}
}
