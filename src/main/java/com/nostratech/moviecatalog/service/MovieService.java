package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.dto.movie.MovieCreateRequestDTO;
import com.nostratech.moviecatalog.dto.movie.MovieDetailResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface MovieService {
	public void createMovie(MovieCreateRequestDTO dto);
	
	public MovieDetailResponseDTO findMovieById(String secureId);
	
	public ResponsePageDTO<MovieResponseDTO> findMovies(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String title
	);
	
	public ResponsePageDTO<MovieResponseDTO> findMoviesByPersonel(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String personelId
	);
	
	public ResponsePageDTO<MovieResponseDTO> findMoviesByGenre(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String title,
		Long id
	);
	
	public ResponsePageDTO<MovieResponseDTO> findMoviesByReviewCount(
		Integer page, 
		Integer limit
	);
	
	public ResponsePageDTO<MovieResponseDTO> findMoviesByRating(
		Integer page, 
		Integer limit
	);
	
	public void updateMovie(String secureId, MovieUpdateRequestDTO dto);

	public void deleteMovie(String secureId);
}
