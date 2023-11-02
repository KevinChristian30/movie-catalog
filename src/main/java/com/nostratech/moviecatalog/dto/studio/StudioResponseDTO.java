package com.nostratech.moviecatalog.dto.studio;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudioResponseDTO implements Serializable {
	private static final long serialVersionUID = -4419617093234976430L;
	
	private String id;
	
	private String name;
	
	private String studioTypeName;
	
	private Integer seatCount;
}
