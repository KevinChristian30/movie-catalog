package com.nostratech.moviecatalog.dto.location;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocationDTO implements Serializable {
	private static final long serialVersionUID = 8545923791585071826L;

	private String province;
	
	private String city;
	
	private String district;
	
	private String neighborhood;
	
	private String postalCode;
}
