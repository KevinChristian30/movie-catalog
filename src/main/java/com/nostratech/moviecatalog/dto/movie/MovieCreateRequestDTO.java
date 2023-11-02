package com.nostratech.moviecatalog.dto.movie;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.genre.GenreCreateRequestDTO;
import com.nostratech.moviecatalog.dto.moviepersonelspecialty.MoviePersonelSpecialtyCreateRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MovieCreateRequestDTO(
	@NotBlank
	String title,
	@NotNull
	Long releaseDate,
	@NotBlank
	String description,
	@NotBlank
	String imageUrl,
	@Min(1)
	@NotNull
	Long durationInMinutes,
	@NotEmpty
	List<GenreCreateRequestDTO> genres,
	@NotEmpty
	List<MoviePersonelSpecialtyCreateRequestDTO> moviePersonelSpecialties
) {
}
