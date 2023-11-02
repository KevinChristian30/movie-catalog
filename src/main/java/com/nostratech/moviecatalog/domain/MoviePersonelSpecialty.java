package com.nostratech.moviecatalog.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "movie_personel_specialty")
public class MoviePersonelSpecialty implements Serializable {
	private static final long serialVersionUID = 6830479414620810655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "secure_id")
	private String secureId = UUID.randomUUID().toString();
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "personel_id")
	private Personel personel;

	@ManyToOne
	@JoinColumn(name = "specialty_id")
	private Specialty specialty;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "bio", nullable = true)
	private String bio;
}
