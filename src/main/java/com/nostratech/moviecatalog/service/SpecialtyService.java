package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Specialty;

public interface SpecialtyService {
	public Long getRowCount();
	
	public void createSpecialties(List<Specialty> specialties);
	
	public Specialty findById(Long id);
}
