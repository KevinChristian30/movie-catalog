package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Neighborhood;
import com.nostratech.moviecatalog.dto.city.CityResponseDTO;
import com.nostratech.moviecatalog.dto.district.DistrictResponseDTO;
import com.nostratech.moviecatalog.dto.neighborhood.NeighborhoodResponseDTO;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.NeighborhoodRepository;
import com.nostratech.moviecatalog.service.NeighborhoodService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NeighborhoodServiceImpl implements NeighborhoodService {
	private final NeighborhoodRepository neighborhoodRepository;
		
	@Override
	public Long getRowCount() {
		return neighborhoodRepository.count();				
	}

	@Override
	public void createNeighborhoods(List<Neighborhood> cities) {
		neighborhoodRepository.saveAll(cities);
	}

	@Override
	public Neighborhood findById(Long id) {
		return neighborhoodRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("neighborhood not found"));
	}

	@Override
	public ResponsePageDTO<NeighborhoodResponseDTO> findByDistrictAndName(Integer page, Integer limit, String sortBy,
			String direction, Long districtId, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Neighborhood> result = neighborhoodRepository
			.findByDistrictAndName(districtId, "%" + name + "%", pageable);
		
		List<NeighborhoodResponseDTO> dtos = result.stream().map(p -> {
			NeighborhoodResponseDTO dto = new NeighborhoodResponseDTO();
			dto.setName(p.getName());
			dto.setPostalCode(p.getPostalCode());
			
			DistrictResponseDTO districtDTO = new DistrictResponseDTO();
			
			districtDTO.setName(p.getDistrict().getName());
			CityResponseDTO cityDTO = new CityResponseDTO();
			cityDTO.setName(p.getDistrict().getCity().getName());
			
			districtDTO.setCity(cityDTO);
			
			ProvinceResponseDTO provinceDTO = new ProvinceResponseDTO();
			provinceDTO.setName(p.getDistrict().getCity().getProvince().getName());
			
			cityDTO.setProvince(provinceDTO);
			
			dto.setDistrict(districtDTO);
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
