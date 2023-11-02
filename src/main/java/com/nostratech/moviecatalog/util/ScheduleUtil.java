package com.nostratech.moviecatalog.util;

import com.nostratech.moviecatalog.domain.Schedule;
import com.nostratech.moviecatalog.dto.schedule.ScheduleResponseDTO;

public class ScheduleUtil {
	public static ScheduleResponseDTO scheduleToResponseDTO(Schedule schedule) {
		ScheduleResponseDTO dto = new ScheduleResponseDTO();
	
		dto.setId(schedule.getSecureId());
		dto.setMovieTitle(schedule.getMovie().getTitle());
		dto.setMovieImageUrl(schedule.getMovie().getImageUrl());
		dto.setTheatreName(schedule.getStudio().getTheatre().getName());
		dto.setStudioName(schedule.getStudio().getName());
		dto.setStartTime(TimeUtil.localDateTimeToLong(schedule.getStartTime()));
		dto.setEndTime(TimeUtil.localDateTimeToLong(schedule.getEndTime()));
		
		return dto;
	}
}
