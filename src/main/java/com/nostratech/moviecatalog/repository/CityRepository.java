package com.nostratech.moviecatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {
	@Query("SELECT c from City c "
			+ "WHERE c.province.id = :provinceId "
			+ "AND c.name ILIKE :name")
	public Page<City> findByProvinceAndName(
		Long provinceId, String name, Pageable pageable );
}
