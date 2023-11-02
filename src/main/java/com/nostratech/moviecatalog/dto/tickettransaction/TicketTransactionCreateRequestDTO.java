package com.nostratech.moviecatalog.dto.tickettransaction;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TicketTransactionCreateRequestDTO (
	List<String> ticketIds
){
}
