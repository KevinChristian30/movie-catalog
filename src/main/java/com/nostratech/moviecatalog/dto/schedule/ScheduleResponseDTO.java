package com.nostratech.moviecatalog.dto.schedule;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
	
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleResponseDTO {
	private String id;
	
	private String movieTitle;
	
	private String movieImageUrl;
	
	private String theatreName;
	
	private String studioName;
	
	private Long startTime;
	
	private Long endTime;
}
