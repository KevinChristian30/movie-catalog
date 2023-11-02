package com.nostratech.moviecatalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.GenreService;
import com.nostratech.moviecatalog.service.MovieService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GenreResource {
	private final GenreService genreService;
	private final MovieService movieService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/genres")
	public ResponseEntity<ResponsePageDTO<GenreResponseDTO>> findGenresByName(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "name", required = true, defaultValue = "") String name
	) {
		return ResponseEntity.ok(genreService.findGenresByName(page, limit, name));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/genres/{id}/movies")
	public ResponseEntity<ResponsePageDTO<MovieResponseDTO>> getMoviesByGenre(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "title", required = false, defaultValue = "") String title,
		@PathVariable(name = "id") Long id
	) {
		return ResponseEntity.ok(movieService.findMoviesByGenre(page, limit, sortBy, direction, title, id));
	}
}
