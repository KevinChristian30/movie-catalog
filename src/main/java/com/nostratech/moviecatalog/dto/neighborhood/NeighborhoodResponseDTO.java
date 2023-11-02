package com.nostratech.moviecatalog.dto.neighborhood;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.district.DistrictResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NeighborhoodResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String postalCode;
	
	private DistrictResponseDTO district;
}
