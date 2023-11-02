package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.StudioType;

public interface StudioTypeRepository extends JpaRepository<StudioType, Long> {
	public Optional<StudioType> findById(Long id);
}
