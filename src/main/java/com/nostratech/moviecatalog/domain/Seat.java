package com.nostratech.moviecatalog.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "seats")
public class Seat implements Serializable {
	private static final long serialVersionUID = 7130412767092143586L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
	@SequenceGenerator(name = "seat_generator", sequenceName = "seat_id_seq")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "studio_type_id", nullable = false)
	private StudioType studioType;
	
	@OneToMany(mappedBy = "seat")
	private List<Ticket> tickets;
}
