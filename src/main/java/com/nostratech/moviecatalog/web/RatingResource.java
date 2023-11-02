package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.rating.RatingByUserResponseDTO;
import com.nostratech.moviecatalog.dto.rating.RatingCreateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.RatingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RatingResource {
	private final RatingService userMovieRatingService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/v1/ratings")
	public ResponseEntity<Void> create(
		@RequestBody
		@Valid
		RatingCreateRequestDTO dto
	) {
		userMovieRatingService.createUserMovieRating(dto);
		
		return ResponseEntity.created(URI.create("/v1/ratings")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/ratings")
	public ResponseEntity<ResponsePageDTO<RatingByUserResponseDTO>> getByUser(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "review") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction
	) {
		return ResponseEntity
			.ok(
				userMovieRatingService
					.findByUser(page, limit, sortBy, direction));
	}
}
