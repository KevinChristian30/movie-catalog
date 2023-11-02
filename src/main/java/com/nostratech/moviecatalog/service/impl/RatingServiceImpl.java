package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.config.ApplicationProperties;
import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.domain.UserMovieRating;
import com.nostratech.moviecatalog.dto.rating.RatingByMovieResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingByUserResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingCreateRequestDTO;
import com.nostratech.moviecatalog.dto.rating.RatingUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.MovieRepository;
import com.nostratech.moviecatalog.repository.UserMovieRatingRepository;
import com.nostratech.moviecatalog.service.RatingService;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.util.MovieUtil;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
	private UserMovieRatingRepository userMovieRatingRepository;
	private MovieRepository movieRepository;
	
	private UserService userService;
	
	private ApplicationProperties applicationProperties;
	
	@Override
	public void createUserMovieRating(RatingCreateRequestDTO dto) {
		UserMovieRating userMovieRating = new UserMovieRating();
		
		Movie movie = movieRepository
			.findBySecureId(dto.movieId())
			.orElseThrow(() -> new NotFoundException("movie not found"));
		User user = userService.getCurrentUser();
		
		List<UserMovieRating> movieRatings = userMovieRatingRepository
			.getMovieRatingsDeletedAndNotDeleted(movie.getId(), user.getId());
		if (movieRatings.size() >= applicationProperties.getReviewEditLimit()) {
			throw new BadRequestException(
				String.format(
						"can't update review more than %d times", 
						applicationProperties.getReviewEditLimit())
			);
		}
		userMovieRatingRepository.deleteAll(movieRatings);
		
		userMovieRating.setMovie(movie);
		userMovieRating.setUser(user);
		userMovieRating.setRating(dto.rating());
		userMovieRating.setReview(dto.review());
		
		userMovieRatingRepository.save(userMovieRating);
	}

	
	@Override
	public ResponsePageDTO<RatingByMovieResponseDTO> findByMovie(String id, Integer page, Integer limit, String sortBy,
			String direction) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Movie movie = movieRepository
			.findBySecureId(id)
			.orElseThrow(() -> new NotFoundException("movie not found"));
		
		Page<UserMovieRating> result = 
			userMovieRatingRepository
				.findByMovie(movie, pageable);
		
		List<RatingByMovieResponseDTO> dtos = result.stream().map(p -> {
			RatingByMovieResponseDTO dto = new RatingByMovieResponseDTO();
			
			dto.setUsername(p.getUser().getUsername());
			dto.setReview(p.getReview());
			dto.setRating(p.getRating());
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	
	@Override
	public ResponsePageDTO<RatingByUserResponseDTO> findByUser(Integer page, Integer limit, String sortBy,
			String direction) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		User user = userService.getCurrentUser();
		
		Page<UserMovieRating> result = 
			userMovieRatingRepository
			.findByUser(user, pageable);
		
		List<RatingByUserResponseDTO> dtos = result.stream().map(p -> {
			RatingByUserResponseDTO dto = new RatingByUserResponseDTO();

			dto.setMovie(MovieUtil.movieToResponseDTO(p.getMovie()));
			dto.setReview(p.getReview());
			dto.setRating(p.getRating());
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	
	public List<RatingByUserResponseDTO> findHistory(String movieId) {
		Movie movie = movieRepository
			.findBySecureId(movieId)
			.orElseThrow(() -> new NotFoundException("movie not found"));
		
		User user = userService.getCurrentUser();
		
		List<UserMovieRating> umr = 
			userMovieRatingRepository
				.getMovieRatingsDeletedAndNotDeleted(movie.getId(), user.getId());
		
		List<RatingByUserResponseDTO> dtos = umr.stream().map(p -> {
			RatingByUserResponseDTO dto = new RatingByUserResponseDTO();

			dto.setMovie(MovieUtil.movieToResponseDTO(p.getMovie()));
			dto.setReview(p.getReview());
			dto.setRating(p.getRating());
			
			return dto;
		}).collect(Collectors.toList());
		
		return dtos;
	}
	
	@Override
	public void update(String movieId, RatingUpdateRequestDTO dto) {
		User user = userService.getCurrentUser();
		Movie movie = movieRepository
				.findBySecureId(movieId)
				.orElseThrow(() -> new NotFoundException("movie not found"));
		
		UserMovieRating userMovieRating = 
			userMovieRatingRepository
				.findByMovieIdAndUserId(movie.getId(), user.getId())
				.orElseThrow(() -> new NotFoundException("rating and review doesn't exist"));
		
		userMovieRating.setRating(dto.rating() == null ? userMovieRating.getRating() : dto.rating());
		userMovieRating.setReview(dto.review() == null ? userMovieRating.getReview() : dto.review());
		
		userMovieRatingRepository.save(userMovieRating);
	}

	
	@Override
	public void delete(String movieId) {
		User user = userService.getCurrentUser();
		Movie movie = movieRepository
				.findBySecureId(movieId)
				.orElseThrow(() -> new NotFoundException("movie not found"));
		
		UserMovieRating userMovieRating = 
			userMovieRatingRepository
				.findByMovieIdAndUserId(movie.getId(), user.getId())
				.orElseThrow(() -> new NotFoundException("rating and review doesn't exist"));
		
		userMovieRatingRepository.delete(userMovieRating);
	}
}
