package com.nostratech.moviecatalog.dto.district;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.city.CityResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DistrictResponseDTO implements Serializable {
	private static final long serialVersionUID = 7487256603579373989L;
	
	private String name;
	
	private CityResponseDTO city;
}
