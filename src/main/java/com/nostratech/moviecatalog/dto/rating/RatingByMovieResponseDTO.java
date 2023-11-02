package com.nostratech.moviecatalog.dto.rating;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingByMovieResponseDTO implements Serializable {
	private static final long serialVersionUID = 690024777359139828L;
	
	private String username;
	
	private Integer rating;
	
	private String review;
}
