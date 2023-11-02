package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.studio.StudioResponseDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreCreateRequestDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreResponseDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.StudioService;
import com.nostratech.moviecatalog.service.TheatreService;
import com.nostratech.moviecatalog.validation.annotation.ValidTheatreManager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Validated
public class TheatreResource {
	private final TheatreService theatreService;
	private final StudioService studioService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/v1/theatres")
	public ResponseEntity<Object> create(
		@RequestBody
		@Valid
		TheatreCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		theatreService.createTheatre(dto);
		
		return ResponseEntity.created(URI.create("/v1/theatres")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/theatres/{id}")
	public ResponseEntity<TheatreResponseDTO> getDetail(
		@PathVariable(name = "id")
		String id
	) {
		return ResponseEntity.ok(theatreService.findBySecureId(id));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/theatres")
	public ResponseEntity<ResponsePageDTO<TheatreResponseDTO>> getTheatres(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(theatreService.findTheatres(page, limit, sortBy, direction, name));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/theatres-by-user")
	public ResponseEntity<ResponsePageDTO<TheatreResponseDTO>> getTheatresByUser(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(theatreService.findTheatresByUser(page, limit, sortBy, direction, name));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/v1/theatres/{id}")
	public ResponseEntity<Void> updateTheatre(
		@PathVariable(name = "id")
		@ValidTheatreManager
		String id,	
		@RequestBody
		TheatreUpdateRequestDTO dto
	) {
		theatreService.updateTheatre(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/v1/theatres/{id}")
	public ResponseEntity<Void> deleteTheatre(
		@PathVariable(name = "id")
		@ValidTheatreManager
		String id
	) {
		theatreService.deleteTheatre(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("v1/theatres/{id}/studios")
	public ResponseEntity<ResponsePageDTO<StudioResponseDTO>> get(
		@PathVariable(name = "id") String id,
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(studioService.getStudios(id, page, limit, sortBy, direction, name));
	}
}
