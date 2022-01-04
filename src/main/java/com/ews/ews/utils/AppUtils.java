package com.ews.ews.utils;

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
}
