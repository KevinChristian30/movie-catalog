package com.nostratech.moviecatalog.dto.genre;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GenreResponseDTO implements Serializable {
	private static final long serialVersionUID = -5729322417543505476L;
	
	private String genreName;
}
