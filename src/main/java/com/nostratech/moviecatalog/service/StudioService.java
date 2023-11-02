package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.domain.Studio;
import com.nostratech.moviecatalog.dto.studio.StudioCreateRequestDTO;
import com.nostratech.moviecatalog.dto.studio.StudioResponseDTO;
import com.nostratech.moviecatalog.dto.studio.StudioUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface StudioService {
	public void createStudio(StudioCreateRequestDTO dto);
	
	public StudioResponseDTO getStudioById(String id);

	public StudioResponseDTO toResponseDTO(Studio studio);
	
	public ResponsePageDTO<StudioResponseDTO> getStudios(
		String id,
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		String name
	);
	
	public ResponsePageDTO<StudioResponseDTO> findStudiosByUser(
		String id,
		Integer page,
		Integer limit,
		String sortBy,
		String direction,
		String name
	);
	
	public void updateStudio(String id, StudioUpdateRequestDTO dto);
	
	public void deleteStudio(String id);
}
