package com.nostratech.moviecatalog.dto.province;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProvinceResponseDTO implements Serializable {
	private static final long serialVersionUID = -5756104311688612423L;
	
	private String name;
}
