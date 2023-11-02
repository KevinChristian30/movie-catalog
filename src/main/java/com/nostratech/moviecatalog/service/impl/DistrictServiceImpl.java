package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.District;
import com.nostratech.moviecatalog.dto.city.CityResponseDTO;
import com.nostratech.moviecatalog.dto.district.DistrictResponseDTO;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.repository.DistrictRepository;
import com.nostratech.moviecatalog.service.DistrictService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {
	private final DistrictRepository districtRepository;

	@Override
	public Long getRowCount() {
		return districtRepository.count();
	}

	@Override
	public void createDistricts(List<District> postalcodes) {
		districtRepository.saveAll(postalcodes);
	}

	@Override
	public List<District> getAll() {
		return districtRepository.findAll();
	}

	@Override
	public ResponsePageDTO<DistrictResponseDTO> findByCityAndName(Integer page, Integer limit, String sortBy,
			String direction, Long cityId, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<District> result = districtRepository
			.findByCityAndName(cityId, "%" + name + "%", pageable);
		
		List<DistrictResponseDTO> dtos = result.stream().map(p -> {
			DistrictResponseDTO dto = new DistrictResponseDTO();
			
			dto.setName(p.getName());
			CityResponseDTO cityDTO = new CityResponseDTO();
			cityDTO.setName(p.getCity().getName());
			
			dto.setCity(cityDTO);
			
			ProvinceResponseDTO provinceDTO = new ProvinceResponseDTO();
			provinceDTO.setName(p.getCity().getProvince().getName());
			
			cityDTO.setProvince(provinceDTO);
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
