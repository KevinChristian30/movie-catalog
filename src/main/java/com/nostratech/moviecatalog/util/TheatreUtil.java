package com.nostratech.moviecatalog.util;

import com.nostratech.moviecatalog.domain.Theatre;
import com.nostratech.moviecatalog.dto.location.LocationDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreResponseDTO;

public class TheatreUtil {
	public static TheatreResponseDTO theatreToResponseDTO(Theatre theatre) {
		TheatreResponseDTO dto = new TheatreResponseDTO();
		
		dto.setId(theatre.getSecureId());
		dto.setName(theatre.getName());
		dto.setFullAddress(theatre.getFullAddress());

		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setNeighborhood(
			theatre.getNeighborhood().getName());
		locationDTO.setDistrict(
			theatre.getNeighborhood().getDistrict().getName());
		locationDTO.setCity(
			theatre.getNeighborhood().getDistrict().getCity().getName());		
		locationDTO.setProvince(
			theatre.getNeighborhood().getDistrict().getCity()
			.getProvince().getName());
		locationDTO.setPostalCode(theatre.getNeighborhood().getPostalCode());
		dto.setLocation(locationDTO);
		
		return dto;
	}
}
