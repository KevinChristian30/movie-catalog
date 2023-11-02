package com.nostratech.moviecatalog.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
	public static boolean isInFuture(Long epochSeconds) {
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		Long now = LocalDateTime.now().atZone(zoneId).toEpochSecond();
		
		return epochSeconds > now;
	}
	
	public static LocalDateTime longToLocalDateTime(Long epochSeconds) {
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		
		return 
			LocalDateTime
			.ofInstant(
				Instant.ofEpochSecond(epochSeconds), zoneId);
	}
	
	public static Long localDateTimeToLong(LocalDateTime time) {
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		
		return time.atZone(zoneId).toEpochSecond();	
	}
	
	public static LocalDate longToLocalDate(Long epocchSeconds) {
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		
		return Instant
				.ofEpochSecond(epocchSeconds)
				.atZone(zoneId)
				.toLocalDate();
	}
	
	public static Long localDateToLong(LocalDate date) {
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		
		return date.atStartOfDay(zoneId).toEpochSecond();
	}
}
