package com.nostratech.moviecatalog.dto.theatre;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TheatreCreateRequestDTO (
	@NotBlank
	String name,
	@NotBlank
	String fullAddress,
	@NotNull
	Long neighborhoodId
) {
	
}
