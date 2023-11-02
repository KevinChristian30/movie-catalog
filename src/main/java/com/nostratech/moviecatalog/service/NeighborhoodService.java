package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Neighborhood;
import com.nostratech.moviecatalog.dto.neighborhood.NeighborhoodResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface NeighborhoodService {
	public Long getRowCount();
	
	public void createNeighborhoods(List<Neighborhood> cities);
	
	public Neighborhood findById(Long id);
	
	public ResponsePageDTO<NeighborhoodResponseDTO> findByDistrictAndName(
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		Long districtId,
		String name
	);
}
