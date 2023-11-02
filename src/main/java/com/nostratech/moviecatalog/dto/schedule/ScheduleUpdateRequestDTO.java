package com.nostratech.moviecatalog.dto.schedule;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ScheduleUpdateRequestDTO (
	Long startTime,
	Long endTIme
) {

}
