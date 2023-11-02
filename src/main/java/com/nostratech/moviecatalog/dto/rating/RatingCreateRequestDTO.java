package com.nostratech.moviecatalog.dto.rating;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RatingCreateRequestDTO(
	@NotNull
	String movieId,
	@NotNull
	@Min(1)
	@Max(10)
	Integer rating,
	@NotNull
	String review
) {
}
