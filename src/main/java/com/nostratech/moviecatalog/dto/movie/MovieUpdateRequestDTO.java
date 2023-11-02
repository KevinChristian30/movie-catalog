package com.nostratech.moviecatalog.dto.movie;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.genre.GenreCreateRequestDTO;
import com.nostratech.moviecatalog.dto.moviepersonelspecialty.MoviePersonelSpecialtyUpdateRequestDTO;

import jakarta.validation.constraints.Min;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MovieUpdateRequestDTO(
	String title,
	Long releaseDate,
	String description,
	String imageUrl,
	@Min(1)
	Long durationInMinutes,
	List<GenreCreateRequestDTO> genres,
	List<MoviePersonelSpecialtyUpdateRequestDTO> moviePersonelSpecialties
) {
}
