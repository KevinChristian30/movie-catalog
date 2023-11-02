package com.nostratech.moviecatalog.dto.seat;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SeatCreateRequestDTO (
	@NotBlank
	String name
) {
	
}
