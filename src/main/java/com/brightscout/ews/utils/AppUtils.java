package com.brightscout.ews.utils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class AppUtils {
	public static String decodeBase64String(String str) {
		return new String(Base64.getDecoder().decode(str));
	}

	public static Date parseDateString(String date) {
		return Date.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(date, Instant::from));
	}
}
