package com.nostratech.moviecatalog.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "studio_types")
public class StudioType implements Serializable {
	private static final long serialVersionUID = -107375701152137913L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "width")
	private Long width;
	
	@Column(name = "height")
	private Long lengrh;
	
	@OneToMany(mappedBy = "studioType")
	private List<Studio> studios;
	
	@OneToMany(mappedBy = "studioType", cascade = CascadeType.ALL)
	private List <Seat> seats;
	
	public StudioType(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
