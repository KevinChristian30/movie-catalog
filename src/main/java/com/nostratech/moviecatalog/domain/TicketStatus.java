package com.nostratech.moviecatalog.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ticket_statuses")
public class TicketStatus {
	@Id
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "ticketStatus")
	private List<Ticket> tickets;
	
	public TicketStatus(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
