package com.nostratech.moviecatalog.dto.city;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.province.ProvinceResponseDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CityResponseDTO implements Serializable {
	private static final long serialVersionUID = -592442139725251710L;
	
	private String name;
	
	private ProvinceResponseDTO province;
}
