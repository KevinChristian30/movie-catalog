package com.nostratech.moviecatalog.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Ticket;
import com.nostratech.moviecatalog.domain.TicketStatus;
import com.nostratech.moviecatalog.domain.TicketTransaction;
import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionCreateRequestDTO;
import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.TicketRepository;
import com.nostratech.moviecatalog.repository.TicketStatusRepository;
import com.nostratech.moviecatalog.repository.TicketTransactionRepository;
import com.nostratech.moviecatalog.service.TicketTransactionService;
import com.nostratech.moviecatalog.util.PaginationUtil;
import com.nostratech.moviecatalog.util.TimeUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketTransactionServiceImpl implements TicketTransactionService {
	private final TicketTransactionRepository ticketTransactionRepository;
	private final TicketRepository ticketRepository;
	private final TicketStatusRepository ticketStatusRepository;

	@Override
	@Transactional
	public void createTransaction(TicketTransactionCreateRequestDTO dto) {
		List<Ticket> tickets = ticketRepository.findBySecureIdIn(dto.ticketIds());
		
		TicketStatus ticketStatus = ticketStatusRepository
			.findByNameLikeIgnoreCase("Sold")
			.orElseThrow(() -> new NotFoundException("ticket status not found"));
		
		for (Ticket ticket : tickets) { 
			if (ticket
					.getTicketStatus()
					.getName()
					.equals(ticketStatus.getName())) {
				throw new BadRequestException("ticket is already sold");
			}
			
			ticket.setTicketStatus(ticketStatus);
		}
		ticketRepository.saveAll(tickets);
		
		List<TicketTransaction> ticketTransactions = tickets.stream().map(t -> {
			TicketTransaction ticketTransaction = new TicketTransaction();
			
			ticketTransaction.setTicket(t);
			ticketTransaction.setTransactionTime(LocalDateTime.now());
			
			return ticketTransaction;
		}).collect(Collectors.toList());
		ticketTransactionRepository.saveAll(ticketTransactions);
	}

	@Override
	public TicketTransactionResponseDTO toResponseDTO(TicketTransaction ticketTransaction) {
		TicketTransactionResponseDTO dto = new TicketTransactionResponseDTO();
		
		dto.setId(ticketTransaction.getSecureId());
		dto.setMovieTitle(ticketTransaction.getTicket().getSchedule().getMovie().getTitle());
		dto.setStudioNmame(ticketTransaction.getTicket().getSchedule().getStudio().getName());
		dto.setTheatreName(ticketTransaction.getTicket().getSchedule().getStudio().getTheatre().getName());
		dto.setTransactionDate(TimeUtil.localDateTimeToLong(ticketTransaction.getTransactionTime()));
		
		return dto;
	}
	
	@Override
	public ResponsePageDTO<TicketTransactionResponseDTO> getTransactions(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction,
		String movieId,
		String theatreId,
		Long transactionDate
	) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		LocalDateTime time = transactionDate != null ? 
			TimeUtil.longToLocalDateTime(transactionDate) : null;
		Page<TicketTransaction> result = 
			ticketTransactionRepository
				.findTicketTransactions(
					movieId, 
					theatreId, 
					time,
					pageable
				);
		
		List<TicketTransactionResponseDTO> dtos = result.stream().map(t -> {
			return toResponseDTO(t);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
