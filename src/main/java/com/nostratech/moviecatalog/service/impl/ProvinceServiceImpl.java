package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Province;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.repository.ProvinceRepository;
import com.nostratech.moviecatalog.service.ProvinceService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
	private final ProvinceRepository provinceRepository;

	@Override
	public Long getRowCount() {
		return provinceRepository.count();
	}

	@Override
	public void createProvinces(List<Province> provinces) {
		provinceRepository.saveAll(provinces);
	}

	@Override
	public List<Province> getAll() {
		return provinceRepository.findAll();
	}

	@Override
	public ResponsePageDTO<ProvinceResponseDTO> findByName(Integer page, Integer limit, String sortBy, String direction,
			String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Province> result = provinceRepository
			.findByNameLikeIgnoreCase("%" + name+ "%", pageable);
		
		List<ProvinceResponseDTO> dtos = result.stream().map(p -> {
			ProvinceResponseDTO dto = new ProvinceResponseDTO();
			
			dto.setName(p.getName());
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
