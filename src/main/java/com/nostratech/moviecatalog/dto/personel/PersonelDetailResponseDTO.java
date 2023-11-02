package com.nostratech.moviecatalog.dto.personel;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonelDetailResponseDTO implements Serializable {
	private static final long serialVersionUID = -4241558305610935577L;

	private String id;
	
	private String name;
	
	private String imageUrl;
	
	private String bio;
	
	private Long dateOfBirth;
	
	private String gender;
	
	private Set<String> specialties;
}
