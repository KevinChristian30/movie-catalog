package com.nostratech.moviecatalog.util;

import java.util.stream.Collectors;

import com.nostratech.moviecatalog.domain.Personel;
import com.nostratech.moviecatalog.dto.personel.PersonelDetailResponseDTO;
import com.nostratech.moviecatalog.dto.personel.PersonelResponseDTO;

public class PersonelUtil {
	public static PersonelResponseDTO personelToResponseDTO(Personel personel) {
		PersonelResponseDTO dto = new PersonelResponseDTO();
		
		dto.setId(personel.getSecureId());
		dto.setName(personel.getName());
		dto.setImageUrl(personel.getImageUrl());
		
		return dto;
	}
	
	public static PersonelDetailResponseDTO personelToDetailResponseDTO(Personel personel) {
		PersonelDetailResponseDTO dto = new PersonelDetailResponseDTO();
		
		dto.setId(personel.getSecureId());
		dto.setName(personel.getName());
		dto.setImageUrl(personel.getImageUrl());
		dto.setBio(personel.getBio());
		dto.setDateOfBirth(TimeUtil.localDateToLong(personel.getDateOfBirth()));
		dto.setGender(personel.getGender());
		dto.setSpecialties(personel.getMoviePersonelSpecialties().stream().map(mps -> {
			return mps.getSpecialty().getName();
		}).collect(Collectors.toSet()));

		return dto;
	}
}
