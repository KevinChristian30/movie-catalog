package com.nostratech.moviecatalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.ticket.TicketResponseDTO;
import com.nostratech.moviecatalog.service.TicketService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TicketResource {
	private final TicketService ticketService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/tickets/{id}")
	public ResponseEntity<TicketResponseDTO> get(
		@PathVariable(name = "id")
		String id
	) {
		return ResponseEntity.ok(ticketService.getTicketById(id));
	}
}
