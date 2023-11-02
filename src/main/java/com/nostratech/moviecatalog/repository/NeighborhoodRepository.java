package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Neighborhood;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {
	public Optional<Neighborhood> findById(Long id);
	
	@Query("SELECT n FROM Neighborhood n "
			+ "WHERE n.district.id = :districtId "
			+ "AND n.name ILIKE :name")
	public Page<Neighborhood> findByDistrictAndName(
		Long districtId, String name, Pageable pageable );
}
