package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.City;
import com.nostratech.moviecatalog.dto.city.CityResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface CityService {
	public Long getRowCount();
	
	public void createCities(List<City> cities);
	
	public List<City> getAll();
	
	public ResponsePageDTO<CityResponseDTO> findByProvinceAndName(
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		Long provinceId,
		String name
	);
}
