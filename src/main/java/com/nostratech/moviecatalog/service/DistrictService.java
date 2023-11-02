package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.District;
import com.nostratech.moviecatalog.dto.district.DistrictResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface DistrictService {
	public Long getRowCount();
	
	public void createDistricts(List<District> postalcodes);

	public List<District> getAll();

	public ResponsePageDTO<DistrictResponseDTO> findByCityAndName(
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		Long cityId,
		String name
	);
}
