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

import com.nostratech.moviecatalog.dto.studio.StudioCreateRequestDTO;
import com.nostratech.moviecatalog.dto.studio.StudioResponseDTO;
import com.nostratech.moviecatalog.dto.studio.StudioUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.StudioService;
import com.nostratech.moviecatalog.validation.annotation.ValidStudioManager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Validated
public class StudioResource {
	private final StudioService studioService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/v1/studios")
	public ResponseEntity<Object> create(
		@RequestBody
		@Valid
		StudioCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		studioService.createStudio(dto);
		
		return ResponseEntity.created(URI.create("/v1/studios")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/studios-by-user")
	public ResponseEntity<ResponsePageDTO<StudioResponseDTO>> findStudiosByUser(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(studioService.findStudiosByUser(name, page, limit, sortBy, direction, name));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/studios/{id}")
	public ResponseEntity<StudioResponseDTO> get(
		@PathVariable(name = "id")
		String id
	) {
		return ResponseEntity.ok(studioService.getStudioById(id));
	}

	@PreAuthorize("isAuthenticated()")
	@PatchMapping("v1/studios/{id}")
	public ResponseEntity<Object> update(
		@PathVariable(name = "id")
		@ValidStudioManager
		String id,
		@RequestBody
		@Valid
		StudioUpdateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		studioService.updateStudio(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("v1/studios/{id}")
	public ResponseEntity<Void> delete(
		@PathVariable(name = "id")
		@ValidStudioManager
		String id
	) {
		studioService.deleteStudio(id);
		
		return ResponseEntity.ok().build();
	}
}
