package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Schedule;
import com.nostratech.moviecatalog.domain.Ticket;
import com.nostratech.moviecatalog.dto.ticket.TicketResponseDTO;
import com.nostratech.moviecatalog.dto.ticket.TicketScheduleResponseDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.ScheduleRepository;
import com.nostratech.moviecatalog.repository.TicketRepository;
import com.nostratech.moviecatalog.service.TicketService;
import com.nostratech.moviecatalog.util.TimeUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
	private final TicketRepository ticketRepository;
	private final ScheduleRepository scheduleRepository;

	@Override
	public void createTickets(List<Ticket> tickets) {
		ticketRepository.saveAll(tickets);
	}
	
	@Override
	public TicketResponseDTO toResponseDTO(Ticket ticket) {
		TicketResponseDTO dto = new TicketResponseDTO();
		dto.setId(ticket.getSecureId());
		dto.setSeatName(ticket.getSeat().getName());
		dto.setTicketStatusName(ticket.getTicketStatus().getName());
		
		return dto;
	}

	@Override
	public TicketResponseDTO getTicketById(String id) {
		Ticket ticket = ticketRepository
			.findBySecureId(id)
			.orElseThrow(() -> new NotFoundException("ticket not found"));
		
		return toResponseDTO(ticket);
	}

	@Override
	public TicketScheduleResponseDTO getTicketsByScheduleid(String id) {
		Schedule schedule = 
				scheduleRepository
				.findBySecureId(id)
					.orElseThrow(() -> new NotFoundException("schedule not found"));
		
		List<TicketResponseDTO> tickets = schedule.getTickets().stream().map(t -> {
			return toResponseDTO(t);
		}).collect(Collectors.toList());
		
		TicketScheduleResponseDTO dto = new TicketScheduleResponseDTO();
		
		dto.setMovieTitle(schedule.getMovie().getTitle());
		dto.setTheatreName(schedule.getStudio().getTheatre().getName());
		dto.setStudioName(schedule.getStudio().getName());
		dto.setStudioType(schedule.getStudio().getStudioType().getName());
		dto.setStartTime(TimeUtil.localDateTimeToLong(schedule.getStartTime()));
		dto.setEndTime(TimeUtil.localDateTimeToLong(schedule.getEndTime()));
		dto.setTickets(tickets);
		
		return dto;
	}
}
