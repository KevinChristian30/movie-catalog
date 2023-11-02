package com.nostratech.moviecatalog.dto.movie;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieResponseDTO implements Serializable {
	private static final long serialVersionUID = 4358544069998123597L;

	private String id;
	
	private String title;
	
	private String imageUrl;
	
	private String description;
	
	private List<GenreResponseDTO> genres; 
	
	private Double rating;
}
