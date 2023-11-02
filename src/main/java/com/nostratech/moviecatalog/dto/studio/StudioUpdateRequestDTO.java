package com.nostratech.moviecatalog.dto.studio;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StudioUpdateRequestDTO (
	String name
) {

}
