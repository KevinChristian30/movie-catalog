package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionCreateRequestDTO;
import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.TicketTransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TicketTransactionResource {
	private final TicketTransactionService ticketTransactionService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/v1/ticket-transactions")
	public ResponseEntity<Object> buyTicket(
		@RequestBody
		@Valid
		TicketTransactionCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}

		ticketTransactionService.createTransaction(dto);
		
		return ResponseEntity.created(URI.create("/v1/ticket-transactions")).build();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/v1/ticket-transactions")
	public ResponseEntity<ResponsePageDTO<TicketTransactionResponseDTO>> get(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "transactionTime") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "movieId", required = false) String movieId,
		@RequestParam(name = "theatreId", required = false) String theatreId,
		@RequestParam(name = "transactionDate", required = false) Long transactionDate
	) {
		return ResponseEntity.ok(ticketTransactionService
			.getTransactions(page, limit, sortBy, direction, 
				movieId, theatreId, transactionDate));
	}
}
