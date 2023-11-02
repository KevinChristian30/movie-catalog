package com.nostratech.moviecatalog.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	public Optional<Schedule> findBySecureId(String secureId);
	
	@Query("SELECT s FROM Schedule s "
			+ "WHERE s.startTime >= :minStartTime")
	public Page<Schedule> findScheduleWithMinStartTime(LocalDateTime minStartTime, Pageable pageable);
}
