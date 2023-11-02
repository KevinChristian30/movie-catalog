package com.nostratech.moviecatalog.dto.ticket;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketScheduleResponseDTO implements Serializable {
	private static final long serialVersionUID = -356182530119115312L;

	private String movieTitle;
	
	private String theatreName;
	
	private String studioName;
	
	private String studioType;
	
	private Long startTime;
	
	private Long endTime;
	
	private List<TicketResponseDTO> tickets;
}
