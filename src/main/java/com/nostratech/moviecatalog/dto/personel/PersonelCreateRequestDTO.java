package com.nostratech.moviecatalog.dto.personel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PersonelCreateRequestDTO (
	@NotBlank(message = "name must be provided") 
	String name,
	@NotBlank(message = "bio must be provided") 
	@Size(max = 255)
	@NotNull
	String bio,
	@NotNull(message = "date of birth must be provided")
	Long dateOfBirth,
	@NotNull
	String gender,
	@NotNull
	String imageUrl
) {

}