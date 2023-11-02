package com.nostratech.moviecatalog.dto.review;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewResponseDTO implements Serializable {
	private static final long serialVersionUID = -7670453157014301028L;
	
	private String username;
	
	private Integer rating;
	
	private String review;
}
