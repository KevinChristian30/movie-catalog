package com.nostratech.moviecatalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.MoviePersonelSpecialty;
import com.nostratech.moviecatalog.domain.Personel;

public interface MoviePersonelSpecialtyRepository extends JpaRepository<MoviePersonelSpecialty, Long> {
	public void deleteBySecureIdIn(List<String> secureIdList);
	
	public Page<MoviePersonelSpecialty> findByPersonel(Personel personel, Pageable pageable);
}
