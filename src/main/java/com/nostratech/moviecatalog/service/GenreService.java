package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Genre;
import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface GenreService {
	public Long getRowCount();
	
	public void createGenres(List<Genre> genres);
	
	public Genre findById(Long id);
	
	public Genre findByName(String name);
	
	public ResponsePageDTO<GenreResponseDTO> findGenresByName(Integer page, Integer limit, String name);
}
