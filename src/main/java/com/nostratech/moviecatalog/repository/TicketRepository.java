package com.nostratech.moviecatalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	public Optional<Ticket> findBySecureId(String secureId);
	
	public List<Ticket> findBySecureIdIn(List<String> secureIdList);
}
