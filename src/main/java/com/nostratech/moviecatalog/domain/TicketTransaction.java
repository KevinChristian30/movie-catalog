package com.nostratech.moviecatalog.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_transactions")
public class TicketTransaction extends AbstractBaseEntity {
	private static final long serialVersionUID = 3009665393343678169L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
	private Ticket ticket;
	
	private LocalDateTime transactionTime;
}
