package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	public Optional<Genre> findById(Long id);
	
	public Optional<Genre> findByName(String name);
	
	public Page<Genre> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
