package com.nostratech.moviecatalog.dto.moviepersonelspecialty;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MoviePersonelSpecialtyUpdateRequestDTO(
	String secureId,
	Long personelId,
	Long specialtyId,
	String name,
	String bio
) {

}
