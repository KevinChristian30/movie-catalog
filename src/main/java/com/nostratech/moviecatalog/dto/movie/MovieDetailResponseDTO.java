package com.nostratech.moviecatalog.dto.movie;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieDetailResponseDTO implements Serializable {
	private static final long serialVersionUID = 7158104011612480448L;

	private String id;
	
	private String title;
	
	private String imageUrl;

	private String description;
	
	private List<GenreResponseDTO> genres;
	
	private Long releaseDate;
	
	private Long duration;
	
	private List<PersonelResponseDTO> personels;
	
	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class PersonelResponseDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		private String specialtyName;
	}
}
