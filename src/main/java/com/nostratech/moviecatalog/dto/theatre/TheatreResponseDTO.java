   package com.nostratech.moviecatalog.dto.theatre;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nostratech.moviecatalog.dto.location.LocationDTO;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TheatreResponseDTO implements Serializable {
	private static final long serialVersionUID = 8362258234262970695L;

	private String id;
	
	private String name;
	
	private String fullAddress;
	
	private LocationDTO location;
}
