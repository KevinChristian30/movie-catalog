package com.nostratech.moviecatalog.dto.studio;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.validation.annotation.ValidTheatreManager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudioCreateRequestDTO (
	@NotBlank
	String name,
	@NotBlank
	@ValidTheatreManager
	String theatreId,
	@NotNull
	Long studioTypeId
) {

}
