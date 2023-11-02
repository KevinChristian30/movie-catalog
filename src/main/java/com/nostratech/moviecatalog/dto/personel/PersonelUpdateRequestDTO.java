package com.nostratech.moviecatalog.dto.personel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PersonelUpdateRequestDTO (
	String name,
	@Size(max = 255)
	String bio,
	Long dateOfBirth,
	String gender
) {
}