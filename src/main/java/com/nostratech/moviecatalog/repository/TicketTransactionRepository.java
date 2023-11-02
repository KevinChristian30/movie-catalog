package com.nostratech.moviecatalog.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.TicketTransaction;

public interface TicketTransactionRepository extends JpaRepository<TicketTransaction, Long> {
	@Query("SELECT t from TicketTransaction t " 
			+ "WHERE (:movieId IS NULL OR t.ticket.schedule.movie.secureId = :movieId) "
			+ "AND (:theatreId IS NULL OR t.ticket.schedule.studio.theatre.secureId = :theatreId) "
			+ "AND (DATE(:transactionTime) IS NULL OR DATE(t.transactionTime) = DATE(:transactionTime))")
	public Page<TicketTransaction> findTicketTransactions(
		String movieId, 
		String theatreId, 
		LocalDateTime transactionTime,
		Pageable pageable
	);
}
