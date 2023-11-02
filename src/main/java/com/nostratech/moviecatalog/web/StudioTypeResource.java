package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.studiotype.StudioTypeCreateRequestDTO;
import com.nostratech.moviecatalog.service.StudioTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StudioTypeResource {
	public final StudioTypeService studioTypeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/v1/studio-types")
	public ResponseEntity<Object> create(
		@RequestBody
		@Valid
		StudioTypeCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		studioTypeService.createStudioType(dto);
		
		return ResponseEntity.created(
			URI.create("/v1/studio-types")).build();
	}
}
