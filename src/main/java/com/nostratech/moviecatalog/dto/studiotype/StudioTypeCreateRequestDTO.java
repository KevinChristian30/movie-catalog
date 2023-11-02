package com.nostratech.moviecatalog.dto.studiotype;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.seat.SeatCreateRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudioTypeCreateRequestDTO (
	@NotBlank
	String name,
	@NotNull
	@Min(1)
	Long width,
	@NotNull
	@Min(1)
	Long length,
	@NotEmpty
	List<SeatCreateRequestDTO> seats
) {

}
