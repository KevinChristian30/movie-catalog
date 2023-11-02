package com.nostratech.moviecatalog.dto.rating;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingByUserResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private MovieResponseDTO movie;
	
	private Integer rating;
	
	private String review;
}
