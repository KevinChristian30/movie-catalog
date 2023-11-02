package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.City;
import com.nostratech.moviecatalog.dto.city.CityResponseDTO;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.repository.CityRepository;
import com.nostratech.moviecatalog.service.CityService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
	private final CityRepository cityRepository;
	
	@Override
	public Long getRowCount() {
		return cityRepository.count();
	}

	@Override
	public void createCities(List<City> cities) {
		cityRepository.saveAll(cities);
	}

	@Override
	public List<City> getAll() {
		return cityRepository.findAll();
	}

	@Override
	public ResponsePageDTO<CityResponseDTO> findByProvinceAndName(Integer page, Integer limit, String sortBy,
			String direction, Long provinceId, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<City> result = cityRepository
			.findByProvinceAndName(provinceId, "%" + name + "%", pageable);
		
		List<CityResponseDTO> dtos = result.stream().map(p -> {
			CityResponseDTO dto = new CityResponseDTO();
			
			dto.setName(p.getName());
			
			ProvinceResponseDTO provinceDTO = new ProvinceResponseDTO();
			provinceDTO.setName(p.getProvince().getName());
			dto.setProvince(provinceDTO);
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
