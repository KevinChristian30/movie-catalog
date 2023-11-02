package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.dto.schedule.ScheduleCreateRequestDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleResponseDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface ScheduleService {	
	public void createSchedule(ScheduleCreateRequestDTO dto);
	
	public ResponsePageDTO<ScheduleResponseDTO> findSchedules(
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		Long minimumStartTime	
	);
	
	public void updateSchedule(String id, ScheduleUpdateRequestDTO dto);

	public void deleteSchedule(String id);
}
