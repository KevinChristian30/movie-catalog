package com.nostratech.moviecatalog.dto.ticket;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketResponseDTO implements Serializable {
	private static final long serialVersionUID = -2582950215843800161L;

	private String id;
	
	private String seatName;
	
	private String ticketStatusName;
}
