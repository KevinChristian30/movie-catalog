package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Studio;
import com.nostratech.moviecatalog.domain.StudioType;
import com.nostratech.moviecatalog.domain.Theatre;
import com.nostratech.moviecatalog.dto.studio.StudioCreateRequestDTO;
import com.nostratech.moviecatalog.dto.studio.StudioResponseDTO;
import com.nostratech.moviecatalog.dto.studio.StudioUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.StudioRepository;
import com.nostratech.moviecatalog.repository.StudioTypeRepository;
import com.nostratech.moviecatalog.repository.TheatreRepository;
import com.nostratech.moviecatalog.service.StudioService;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudioServiceImpl implements StudioService {
	private final StudioRepository studioRepository;
	private final TheatreRepository theatreRepository;
	private final StudioTypeRepository studioTypeRepository;
	
	private final UserService userService;
	
	@Override
	public void createStudio(StudioCreateRequestDTO dto) {
		Theatre theatre = 
				theatreRepository
				.findBySecureId(dto.theatreId())
				.orElseThrow(() -> new NotFoundException("theatre not found"));
		
		Studio studio = new Studio();
		studio.setName(dto.name());
		studio.setTheatre(theatre);
		
		StudioType studioType =
			studioTypeRepository
			.findById(dto.studioTypeId())
			.orElseThrow(() -> new NotFoundException("studio type not found"));
		studio.setStudioType(studioType);	
		
		studioRepository.save(studio);
	}
	
	@Override
	public StudioResponseDTO toResponseDTO(Studio studio) {
		StudioResponseDTO dto = new StudioResponseDTO();
		
		dto.setId(studio.getSecureId());
		dto.setName(studio.getName());
		dto.setStudioTypeName(studio.getStudioType().getName());
		dto.setSeatCount(studio.getStudioType().getSeats().size());
		
		return dto;
	}

	@Override
	public StudioResponseDTO getStudioById(String id) {
		Studio studio = 
			studioRepository
			.findBySecureId(id)
			.orElseThrow(() -> new NotFoundException("studio not found"));
		
		return toResponseDTO(studio);
	}

	@Override
	public ResponsePageDTO<StudioResponseDTO> getStudios(String id, Integer page, Integer limit, String sortBy,
			String direction, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Studio> result = 
			studioRepository
			.findByTheatreAndName(
				id,
				"%" + name + "%",
				pageable
			);
		
		List<StudioResponseDTO> dtos = result.stream().map(
			s -> toResponseDTO(s)
		).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<StudioResponseDTO> findStudiosByUser(String id, Integer page, Integer limit, String sortBy,
			String direction, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		List<String> theatreIds = userService.getCurrentUserTheatreIds();
		Page<Studio> result = 
			studioRepository
			.findByTheatresAndName(
				theatreIds,
				"%" + name + "%",
				pageable
			);
		
		List<StudioResponseDTO> dtos = result.stream().map(
			s -> toResponseDTO(s)
		).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	@Override
	@Transactional
	public void updateStudio(String id, StudioUpdateRequestDTO dto) {
		Studio studio = 
				studioRepository
				.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("studio not found"));
		
		studio.setName(dto.name() == null ? studio.getName() : dto.name());
		
		studioRepository.save(studio);
	}

	@Override
	public void deleteStudio(String id) {
		Studio studio = 
				studioRepository
				.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("studio not found"));
		
		studioRepository.delete(studio);
	}
}
