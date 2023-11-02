package com.nostratech.moviecatalog.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserCreateRequestDTO (
	@NotBlank
	String username,
	@NotBlank
	String password,
	@NotBlank
	String confirmPassword
) {

}
