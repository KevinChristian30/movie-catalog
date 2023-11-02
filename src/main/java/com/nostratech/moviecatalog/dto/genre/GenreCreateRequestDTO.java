package com.nostratech.moviecatalog.dto.genre;

import jakarta.validation.constraints.NotNull;

public record GenreCreateRequestDTO(
	@NotNull
	Long id
) {

}
