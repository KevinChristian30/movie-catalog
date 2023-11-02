package com.nostratech.moviecatalog.dto.moviepersonelspecialty;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MoviePersonelSpecialtyCreateRequestDTO (
	@NotNull
	Long personelId,
	@NotNull
	Long specialtyId,
	String name,
	String bio
) {
	
}
