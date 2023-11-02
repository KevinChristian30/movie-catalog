package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.dto.theatre.TheatreCreateRequestDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreResponseDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface TheatreService {
	public void createTheatre(TheatreCreateRequestDTO dto);
	
	public TheatreResponseDTO findBySecureId(String id);
	
	public ResponsePageDTO<TheatreResponseDTO> findTheatres(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String name
	);
	
	public ResponsePageDTO<TheatreResponseDTO> findTheatresByUser(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String name
	);
	
	public void updateTheatre(String id, TheatreUpdateRequestDTO dto);
	
	public void deleteTheatre(String id);
}
