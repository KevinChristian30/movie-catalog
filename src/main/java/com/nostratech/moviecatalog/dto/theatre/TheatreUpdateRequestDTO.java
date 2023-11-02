package com.nostratech.moviecatalog.dto.theatre;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TheatreUpdateRequestDTO implements Serializable {
	private static final long serialVersionUID = 3554745203431364763L;

	private String name;

	private String fullAddress;
	
	private Long neighborhoodId;
}
