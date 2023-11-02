package com.nostratech.moviecatalog.dto.manager;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ManagerCreateRequestDTO (
	@NotNull
	String theatreId,
	@NotEmpty
	List<String> usernames
) {

}
