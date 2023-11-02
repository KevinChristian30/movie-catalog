package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Ticket;
import com.nostratech.moviecatalog.dto.ticket.TicketResponseDTO;
import com.nostratech.moviecatalog.dto.ticket.TicketScheduleResponseDTO;

public interface TicketService {
	public void createTickets(List<Ticket> tickets);
	
	public TicketResponseDTO getTicketById(String id);
	
	public TicketScheduleResponseDTO getTicketsByScheduleid(String id);

	public TicketResponseDTO toResponseDTO(Ticket ticket);
}
