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

import com.nostratech.moviecatalog.dto.schedule.ScheduleCreateRequestDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleResponseDTO;
import com.nostratech.moviecatalog.dto.schedule.ScheduleUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.ticket.TicketScheduleResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.service.ScheduleService;
import com.nostratech.moviecatalog.service.TicketService;
import com.nostratech.moviecatalog.validation.annotation.ValidScheduleManager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class ScheduleResource {
	private final ScheduleService scheduleService;
	private final TicketService ticketService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/v1/schedules")
	public ResponseEntity<Void> create(
		@RequestBody
		@Valid
		ScheduleCreateRequestDTO dto,
		Errors errors
	) {
		if (errors.hasErrors()) {
	        return ResponseEntity.badRequest().build();
		}
		
		scheduleService.createSchedule(dto);
		
		return ResponseEntity.created(URI.create("/v1/schedules")).build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/schedules")
	public ResponseEntity<ResponsePageDTO<ScheduleResponseDTO>> get(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "startTime") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "minimumStartTime", required = false, defaultValue = "0") Long minimumStartTime		
	) {
		return ResponseEntity.ok(scheduleService.findSchedules(page, limit, sortBy, direction, minimumStartTime));
	}

	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/v1/schedules/{id}")
	public ResponseEntity<ResponsePageDTO<ScheduleResponseDTO>> update(
		@RequestBody
		ScheduleUpdateRequestDTO dto,
		@PathVariable(name = "id")
		@ValidScheduleManager
		String id
	) {
		scheduleService.updateSchedule(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/v1/schedules/{id}")
	public ResponseEntity<ResponsePageDTO<ScheduleResponseDTO>> delete(
		@PathVariable(name = "id")
		@ValidScheduleManager
		String id
	) {
		scheduleService.deleteSchedule(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/schedules/{id}/tickets")
	public ResponseEntity<TicketScheduleResponseDTO> getTicketsBySchedule(
		@PathVariable(name = "id")
		String id
	) {
		return ResponseEntity.ok(ticketService.getTicketsByScheduleid(id));
	}
}
