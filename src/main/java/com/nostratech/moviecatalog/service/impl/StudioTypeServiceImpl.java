
package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Seat;
import com.nostratech.moviecatalog.domain.StudioType;
import com.nostratech.moviecatalog.dto.studiotype.StudioTypeCreateRequestDTO;
import com.nostratech.moviecatalog.repository.StudioTypeRepository;
import com.nostratech.moviecatalog.service.StudioTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudioTypeServiceImpl implements StudioTypeService {
	private final StudioTypeRepository studioTypeRepository;

	@Override
	public void createStudioType(StudioTypeCreateRequestDTO dto) {
		StudioType studioType = new StudioType();
		
		studioType.setName(dto.name());
		studioType.setLengrh(dto.length());
		studioType.setWidth(dto.width());

		List<Seat> seats = dto.seats().stream().map(s -> {
			Seat seat = new Seat();
			
			seat.setName(s.name());
			seat.setStudioType(studioType);
			
			return seat;
		}).collect(Collectors.toList());
		studioType.setSeats(seats);
		
		studioTypeRepository.save(studioType);
	}
}
