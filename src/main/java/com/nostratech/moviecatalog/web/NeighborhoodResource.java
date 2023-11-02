package com.nostratech.moviecatalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.neighborhood.NeighborhoodResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.NeighborhoodService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NeighborhoodResource {
	private final NeighborhoodService neighborhoodService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/neighborhoods")
	public ResponseEntity<ResponsePageDTO<NeighborhoodResponseDTO>> get(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "districtId", required = true, defaultValue = "1") Long districtId,
		@RequestParam(name = "name", required = false, defaultValue = "") String name
	) {
		return ResponseEntity.ok(
			neighborhoodService
			.findByDistrictAndName(page, limit, sortBy, direction, districtId, name));
	}
}
