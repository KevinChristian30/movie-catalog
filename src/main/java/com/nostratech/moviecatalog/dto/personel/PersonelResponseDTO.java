package com.nostratech.moviecatalog.dto.personel;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonelResponseDTO implements Serializable {
	private static final long serialVersionUID = 6112336678309627990L;

	private String id;
	
	private String name;
	
	private String imageUrl;
}
