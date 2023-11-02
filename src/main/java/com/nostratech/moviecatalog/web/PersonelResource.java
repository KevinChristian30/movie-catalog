
package com.nostratech.moviecatalog.web;

import java.net.URI;

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

import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelCreateRequestDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelDetailResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.MovieService;
import com.nostratech.moviecatalog.service.PersonelService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PersonelResource {
	private final PersonelService personelService;
	private final MovieService movieService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/v1/personels")
	public ResponseEntity<Void> create(
		@RequestBody
		@Valid
		PersonelCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		personelService.create(dto);
		
		return ResponseEntity.created(URI.create("/v1/personels")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/personels/{id}")
	public ResponseEntity<PersonelDetailResponseDTO> get(
		@PathVariable("id")
		String id
	) {
		return ResponseEntity.ok(personelService.findBySecureId(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/v1/personels/{id}")
	public ResponseEntity<Void> update(
		@PathVariable("id")
		String id,
		@RequestBody
		PersonelUpdateRequestDTO dto
	) {
		personelService.update(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/personels")
	public ResponseEntity<ResponsePageDTO<PersonelResponseDTO>> getActors(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = "") String name,
		@RequestParam(name = "dateOfBirth", required = false) Long dateOfBirth
	) {
		return ResponseEntity.ok(
			personelService
			.findPersonels(
				page, limit, sortBy, direction, name, dateOfBirth
			)
		);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/v1/personels/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		personelService.deleteBySecureId(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/personels/{id}/movies")
	public ResponseEntity<ResponsePageDTO<MovieResponseDTO>> getMovies(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@PathVariable(name = "id") String id
	) {
		return ResponseEntity
			.ok(
				movieService
				.findMoviesByPersonel(
					page, limit, sortBy, direction, id
				)
			);
	}
}
