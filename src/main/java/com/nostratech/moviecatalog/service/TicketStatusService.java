package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.TicketStatus;

public interface TicketStatusService {
	public Long getRowCount();
	
	public void createTicketStatuses(List<TicketStatus> ticketStatuses);

	public TicketStatus findByName(String name);
}
