package com.nostratech.moviecatalog.dto.tickettransaction;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketTransactionResponseDTO implements Serializable {
	private static final long serialVersionUID = 8263878869216395595L;
	
	private String id;
	
	private String movieTitle;

	private String theatreName;
	
	private String studioNmame;
	
	private Long transactionDate;
}
