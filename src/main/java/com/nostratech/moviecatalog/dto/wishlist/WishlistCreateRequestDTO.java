package com.nostratech.moviecatalog.dto.wishlist;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record WishlistCreateRequestDTO(
	@NotNull
	String movieId
) {

}
