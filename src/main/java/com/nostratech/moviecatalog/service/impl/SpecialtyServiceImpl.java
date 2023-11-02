package com.nostratech.moviecatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Specialty;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.SpecialtyRepository;
import com.nostratech.moviecatalog.service.SpecialtyService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {
	private final SpecialtyRepository specialtyRepository;
	
	@Override
	public Long getRowCount() {
		return specialtyRepository.count();
	}

	@Override
	public void createSpecialties(List<Specialty> specialties) {
		specialtyRepository.saveAll(specialties);
	}

	@Override
	public Specialty findById(Long id) {
		return specialtyRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("specialty not found"));
	}
}
