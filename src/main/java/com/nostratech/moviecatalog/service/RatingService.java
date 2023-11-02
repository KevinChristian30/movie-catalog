package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.dto.rating.RatingByMovieResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingByUserResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingCreateRequestDTO;
import com.nostratech.moviecatalog.dto.rating.RatingUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface RatingService {
	public void createUserMovieRating(RatingCreateRequestDTO dto);

	public ResponsePageDTO<RatingByMovieResponseDTO> findByMovie(
		String movieid,
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction
	);
	
	public ResponsePageDTO<RatingByUserResponseDTO> findByUser(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction
	);
	
	public List<RatingByUserResponseDTO> findHistory(String movieId);
	
	public void update(String movieId, RatingUpdateRequestDTO dto);
	
	public void delete(String movieId);
}
