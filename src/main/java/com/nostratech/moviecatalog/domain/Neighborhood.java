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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "neighborhoods")
public class Neighborhood implements Serializable {
	private static final long serialVersionUID = -3974036988018436105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "postal_code", nullable = false)
	private String postalCode;
	
	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false)
	private District district;
	
	@OneToMany(mappedBy = "neighborhood")
	private List<Theatre> theatres;

	public Neighborhood(Long id, String name, String postalCode, District district) {
		super();
		this.id = id;
		this.name = name;
		this.postalCode = postalCode;
		this.district = district;
	}
}
