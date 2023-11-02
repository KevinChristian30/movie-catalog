package com.nostratech.moviecatalog.service;

import com.nostratech.moviecatalog.domain.Personel;
import com.nostratech.moviecatalog.dto.personel.PersonelCreateRequestDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelDetailResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;

public interface PersonelService {
	public Personel findById(Long id);
	
	public void create(PersonelCreateRequestDTO dto);
	
	public PersonelDetailResponseDTO findBySecureId(String secureId);

	public void update(String secureId, PersonelUpdateRequestDTO dto);

	public void deleteBySecureId(String secureId);

	public ResponsePageDTO<PersonelResponseDTO> findPersonels(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction, 
		String name,
		Long dateOfBirth
	);
}
