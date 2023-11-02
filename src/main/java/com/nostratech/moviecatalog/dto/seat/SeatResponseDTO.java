package com.nostratech.moviecatalog.dto.seat;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SeatResponseDTO implements Serializable {
	private static final long serialVersionUID = -3092928184971494878L;
		
	private String name;
}
