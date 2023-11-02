package com.nostratech.moviecatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{
	public void deleteByIdIn(List<Long> ids);
}
