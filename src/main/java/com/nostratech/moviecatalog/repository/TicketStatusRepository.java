package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.TicketStatus;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long>{
	public Optional<TicketStatus> findByNameLikeIgnoreCase(String name);
}
