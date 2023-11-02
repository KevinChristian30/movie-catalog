package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
	public Optional<Specialty> findById(Long id);
}
