package com.nostratech.moviecatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.repository.MoviePersonelSpecialtyRepository;
import com.nostratech.moviecatalog.service.MoviePersonelSpecialtyService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MoviePersonelSpecialtyServiceImpl implements MoviePersonelSpecialtyService {
	private final MoviePersonelSpecialtyRepository moviePersonelSpecialtyRepository;

	@Override
	public void deleteBySecureIdList(List<String> secureIds) {
		moviePersonelSpecialtyRepository.deleteBySecureIdIn(secureIds);
	}
}
