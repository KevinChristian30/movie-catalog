package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Theatre;
import com.nostratech.moviecatalog.dto.theatre.TheatreCreateRequestDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreResponseDTO;
import com.nostratech.moviecatalog.dto.theatre.TheatreUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.TheatreRepository;
import com.nostratech.moviecatalog.service.NeighborhoodService;
import com.nostratech.moviecatalog.service.TheatreService;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.util.PaginationUtil;
import com.nostratech.moviecatalog.util.TheatreUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TheatreServiceImpl implements TheatreService {
	private final TheatreRepository theatreRepository;
	
	private final NeighborhoodService neighborhoodService;
	private final UserService userService;
	
	@Override
	public void createTheatre(TheatreCreateRequestDTO dto) {
		Theatre theatre = new Theatre();
		
		theatre.setName(dto.name());
		theatre.setFullAddress(dto.fullAddress());
		theatre.setNeighborhood(neighborhoodService.findById(dto.neighborhoodId()));
		
		theatreRepository.save(theatre);
	}

	@Override
	public TheatreResponseDTO findBySecureId(String id) {
		Theatre theatre = theatreRepository
				.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("theatre not found"));
		
		return TheatreUtil.theatreToResponseDTO(theatre);
	}

	@Override
	public ResponsePageDTO<TheatreResponseDTO> findTheatres(Integer page, Integer limit, String sortBy, String direction,
			String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Theatre> result = theatreRepository.findByNameLikeIgnoreCase("%" + name + "%", pageable);
		
		List<TheatreResponseDTO> dtos = result.stream().map(theatre -> {
			return TheatreUtil.theatreToResponseDTO(theatre);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<TheatreResponseDTO> findTheatresByUser(Integer page, Integer limit, String sortBy,
			String direction, String name) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		List<String> theatreIds = userService.getCurrentUserTheatreIds();
		Page<Theatre> result = 
			theatreRepository
			.findByUserAndName(
				theatreIds, "%" + name + "%", pageable);
		
		List<TheatreResponseDTO> dtos = result.stream().map(theatre -> {
			return TheatreUtil.theatreToResponseDTO(theatre);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	@Override
	public void updateTheatre(String id, TheatreUpdateRequestDTO dto) {
		Theatre theatre = theatreRepository
				.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("theatre not found"));
		
		theatre.setName(
			dto.getName() == null ? theatre.getName() : dto.getName()
		);
		theatre.setFullAddress(
			dto.getFullAddress() == null ? theatre.getFullAddress() : dto.getFullAddress()
		);
		
		if (dto.getNeighborhoodId() != null) {
			theatre.setNeighborhood(
				neighborhoodService.findById(dto.getNeighborhoodId())
			);
		}
		
		theatreRepository.save(theatre);
	}

	@Override
	public void deleteTheatre(String id) {
		Theatre theatre = theatreRepository
				.findBySecureId(id)
				.orElseThrow(() -> new NotFoundException("theatre not found"));
		
		theatreRepository.delete(theatre);
	}
}
