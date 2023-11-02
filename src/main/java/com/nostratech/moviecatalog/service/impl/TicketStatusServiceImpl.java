package com.nostratech.moviecatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.TicketStatus;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.TicketStatusRepository;
import com.nostratech.moviecatalog.service.TicketStatusService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketStatusServiceImpl implements TicketStatusService {
	private final TicketStatusRepository ticketStatusRepository;
	
	@Override
	public Long getRowCount() {
		return ticketStatusRepository.count();
	}

	@Override
	public void createTicketStatuses(List<TicketStatus> ticketStatuses) {
		ticketStatusRepository.saveAll(ticketStatuses);
	}

	@Override
	public TicketStatus findByName(String name) {
		return ticketStatusRepository
				.findByNameLikeIgnoreCase(name)
				.orElseThrow(() -> new NotFoundException("ticket status not found"));
	}
}
