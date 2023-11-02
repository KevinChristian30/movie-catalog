package com.nostratech.moviecatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
	@Query("SELECT d FROM District d "
			+ "WHERE d.city.id = :cityId "
			+ "AND d.name ILIKE :name")
	public Page<District> findByCityAndName(
		Long cityId, String name, Pageable pageable );
}
