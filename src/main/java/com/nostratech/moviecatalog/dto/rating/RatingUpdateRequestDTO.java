package com.nostratech.moviecatalog.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RatingUpdateRequestDTO(
	@Min(1)
	@Max(5)
	Integer rating,
	String review
) {
}
