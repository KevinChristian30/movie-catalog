package com.nostratech.moviecatalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Long>{
	public Optional<Theatre> findBySecureId(String secureId);
	
	public Page<Theatre> findByNameLikeIgnoreCase(String name, Pageable pageable);
	
	@Query("SELECT t FROM Theatre t "
			+ "WHERE t.secureId IN :theatreIds "
			+ "AND t.name ILIKE :name")
	public Page<Theatre> findByUserAndName(List<String> theatreIds, String name, Pageable pageable);
}
