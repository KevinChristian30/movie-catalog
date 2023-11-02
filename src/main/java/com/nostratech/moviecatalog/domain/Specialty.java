package com.nostratech.moviecatalog.domain;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "specialties")
public class Specialty implements Serializable {
	private static final long serialVersionUID = -3800744577612418863L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "specialty")
	private List<MoviePersonelSpecialty> moviePersonelSpecialties;

	public Specialty(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
