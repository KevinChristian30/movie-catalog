package com.nostratech.moviecatalog.util;

import java.util.stream.Collectors;

import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.UserMovieRating;
import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieDetailResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieDetailResponseDTO.PersonelResponseDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieUtil {
	public static MovieResponseDTO movieToResponseDTO(Movie movie) {
		MovieResponseDTO dto = new MovieResponseDTO();
		
		dto.setId(movie.getSecureId());
		dto.setTitle(movie.getTitle());
		dto.setImageUrl(movie.getImageUrl());
		dto.setDescription(movie.getDescription());
		dto.setGenres(movie.getGenres().stream().map(genre -> {
			return new GenreResponseDTO(genre.getName());
		}).collect(Collectors.toList()));
		
		Double rating = Double.valueOf(0);
		for (UserMovieRating umr : movie.getUserMovieRatings()) {
			rating += Double.valueOf(umr.getRating());
		}
		dto.setRating(rating);
		
		return dto;
	}
	
	public static MovieDetailResponseDTO movieToDetailResponseDTO(Movie movie) {
		MovieDetailResponseDTO dto = new MovieDetailResponseDTO();
		
		dto.setId(movie.getSecureId());
		dto.setTitle(movie.getTitle());
		dto.setImageUrl(movie.getImageUrl());
		dto.setDescription(movie.getDescription());
		dto.setGenres(movie.getGenres().stream().map(genre -> {
			return new GenreResponseDTO(genre.getName());
		}).collect(Collectors.toList()));
		dto.setReleaseDate(TimeUtil.localDateToLong(movie.getReleaseDate()));
		dto.setDuration(movie.getDurationInMinutes());
		dto.setPersonels(movie.getMoviePersonelSpecialties().stream().map(mps -> {
			MovieDetailResponseDTO.PersonelResponseDTO personelDTO = 
				new PersonelResponseDTO();
			
			personelDTO.setName(mps.getPersonel().getName());
			personelDTO.setSpecialtyName(mps.getSpecialty().getName());
			
			return personelDTO;
		}).collect(Collectors.toList()));
		
		return dto;
	}
}
