package com.nostratech.moviecatalog.dto.util;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponsePageDTO<T> implements Serializable {
	private static final long serialVersionUID = -8838843278667657519L;
	
	private List<T> data;
	
	private Integer pages;
	
	private Long elements;
}
