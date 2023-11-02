package com.nostratech.moviecatalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
	public Optional<Studio> findBySecureId(String secureId);
	
	@Query("SELECT s FROM Studio s "
			+ "WHERE s.theatre.secureId = :theatreId "
			+ "AND s.name ILIKE :name")
	public Page<Studio> findByTheatreAndName(
		String theatreId, 
		String name, 
		Pageable pageable
	);
	
	@Query("SELECT s FROM Studio s "
			+ "WHERE s.theatre.secureId IN :theatreIds "
			+ "AND s.name ILIKE :name")
	public Page<Studio> findByTheatresAndName(
		List<String> theatreIds,
		String name,
		Pageable pageable
	);
}	
