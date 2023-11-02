package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.domain.TicketTransaction;
import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionCreateRequestDTO;
import com.nostratech.moviecatalog.dto.tickettransaction.TicketTransactionResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface TicketTransactionService {
	public void createTransaction(TicketTransactionCreateRequestDTO dto);
	
	public TicketTransactionResponseDTO toResponseDTO(TicketTransaction ticketTransaction);
	
	public ResponsePageDTO<TicketTransactionResponseDTO> getTransactions(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction,
		String movieId,
		String theatreId,
		Long transactionDate
	);
}
