package com.nostratech.moviecatalog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tickets")
public class Ticket extends AbstractBaseEntity {
	private static final long serialVersionUID = 2887540109013992884L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
	@SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
	
	@ManyToOne
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name = "ticket_status_id", nullable = false)
	private TicketStatus ticketStatus;
	
	@OneToOne(mappedBy = "ticket")
	private TicketTransaction ticketTransaction;
	
	private Long price;
}
