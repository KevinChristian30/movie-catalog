package com.nostratech.moviecatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Province;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
	public Page<Province> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
