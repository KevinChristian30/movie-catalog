package com.nostratech.moviecatalog.dto.schedule;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.validation.annotation.ValidStudioManager;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ScheduleCreateRequestDTO (
	@NotBlank
	String movieId,
	@NotBlank
	@ValidStudioManager
	String studioId,
	@NotNull
	Long startTime,
	@NotNull
	Long endTIme,
	@NotNull
	@Min(1000)
	Long price
) {
	
}
