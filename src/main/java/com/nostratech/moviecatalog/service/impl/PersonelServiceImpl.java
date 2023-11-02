package com.nostratech.moviecatalog.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Personel;
import com.nostratech.moviecatalog.dto.personel.PersonelCreateRequestDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelDetailResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.PersonelRepository;
import com.nostratech.moviecatalog.service.PersonelService;
import com.nostratech.moviecatalog.util.PaginationUtil;
import com.nostratech.moviecatalog.util.PersonelUtil;
import com.nostratech.moviecatalog.util.TimeUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonelServiceImpl implements PersonelService {
	private final PersonelRepository personelRepository;
	
	@Override
	public Personel findById(Long id) {
		return personelRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("personel not found"));
	}
	
	@Override
	public void create(PersonelCreateRequestDTO dto) {
		Personel personel = new Personel();
		personel.setName(dto.name());
		personel.setBio(dto.bio());
		
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		LocalDate date = Instant.ofEpochSecond(dto.dateOfBirth()).atZone(zoneId).toLocalDate();
		personel.setDateOfBirth(date);
		
		personel.setGender(dto.gender());
		personel.setImageUrl(dto.imageUrl());
		
		personelRepository.save(personel);
	}

	@Override
	public PersonelDetailResponseDTO findBySecureId(String secureId) {
		Personel personel = personelRepository
				.findBySecureId(secureId)
				.orElseThrow(() -> new NotFoundException("personel not found"));

		return PersonelUtil.personelToDetailResponseDTO(personel);
	}

	@Override
	public void update(String secureId, PersonelUpdateRequestDTO dto) {
		Personel personel = personelRepository
				.findBySecureId(secureId)
				.orElseThrow(() -> new NotFoundException("personel not found"));
		
		personel.setName(dto.name() == null ? personel.getName() : dto.name());
		personel.setBio(dto.bio() == null ? personel.getBio() : dto.bio());
		
		if (dto.dateOfBirth() != null) {
			ZoneId zoneId = ZoneId.of("Asia/Jakarta");
			LocalDate date = Instant.ofEpochSecond(dto.dateOfBirth()).atZone(zoneId).toLocalDate();
			
			personel.setDateOfBirth(date);
		}
		
		personel.setGender(
			dto.gender() == null ? 
					personel.getGender() : 
					dto.gender());
		
		personelRepository.save(personel);
	}

	@Override
	public void deleteBySecureId(String secureId) {
		Personel personel = personelRepository
				.findBySecureId(secureId)
				.orElseThrow(() -> new NotFoundException("personel not found"));
		
		personelRepository.delete(personel);
	}

	@Override
	public ResponsePageDTO<PersonelResponseDTO> findPersonels(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction,
		String name,
		Long dateOfBirth
	) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Personel> result;
		if (dateOfBirth == null) {
			result = personelRepository
				.findByNameLikeIgnoreCase(
					"%" + name + "%", 
					pageable
				);
		} else {
			result = personelRepository
				.findByNameAndDateOfBirth(
					"%" + name + "%",
					TimeUtil.longToLocalDate(dateOfBirth),
					pageable
				);
		}
		
		List<PersonelResponseDTO> dtos = result.stream().map(p -> {
			return PersonelUtil.personelToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
