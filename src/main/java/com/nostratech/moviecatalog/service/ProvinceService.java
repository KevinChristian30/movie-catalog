package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Province;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface ProvinceService {
	public Long getRowCount();
	
	public List<Province> getAll();
	
	public void createProvinces(List<Province> provinces);
	
	public ResponsePageDTO<ProvinceResponseDTO> findByName(
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		String name		
	);
}
