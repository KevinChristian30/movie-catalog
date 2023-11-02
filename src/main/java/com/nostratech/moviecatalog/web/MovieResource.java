package com.nostratech.moviecatalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.movie.MovieCreateRequestDTO;
import com.nostratech.moviecatalog.dto.movie.MovieDetailResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.rating.RatingByMovieResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingByUserResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.MovieService;
import com.nostratech.moviecatalog.service.RatingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MovieResource {
	private final MovieService movieService;
	private final RatingService ratingService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/v1/movies")
	public ResponseEntity<Object> create(
		@RequestBody
		@Valid
		MovieCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		movieService.createMovie(dto);
		
		return ResponseEntity.created(URI.create("/v1/movies")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/movies/{id}")
	public ResponseEntity<MovieDetailResponseDTO> findById(
		@PathVariable(name = "id") 
		String id
	) {
		return ResponseEntity.ok(movieService.findMovieById(id));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/movies")
	public ResponseEntity<ResponsePageDTO<MovieResponseDTO>> getMovies(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "title", required = false, defaultValue = "") String title
	) {
		ResponsePageDTO<MovieResponseDTO> dto;
		
		if (sortBy.equals("highest-rating")) {
			dto = movieService.findMoviesByRating(page, limit);
		} else if (sortBy.equals("review-count")) {
			dto = movieService.findMoviesByReviewCount(page, limit);
		} else {
			dto = movieService.findMovies(page, limit, sortBy, direction, title);
		}
		
		return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/v1/movies/{id}")
	public ResponseEntity<Void> update(
		@PathVariable(name = "id")
		String id,
		@RequestBody
		@Valid
		MovieUpdateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		movieService.updateMovie(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/v1/movies/{id}")
	public ResponseEntity<Void> delete(
		@PathVariable(name = "id") String id
	) {
		movieService.deleteMovie(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/movies/{id}/ratings")
	public ResponseEntity<ResponsePageDTO<RatingByMovieResponseDTO>> getByMovie(
		@PathVariable(name = "id") String id,
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "review") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction
	) {
		return ResponseEntity
			.ok(
				ratingService
					.findByMovie(id, page, limit, sortBy, direction));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/movies/{id}/ratings-history")
	public ResponseEntity<List<RatingByUserResponseDTO>> getHistory(
		@PathVariable(name = "id") String id
	) {
		return ResponseEntity
			.ok(
				ratingService
					.findHistory(id));
	}
}
