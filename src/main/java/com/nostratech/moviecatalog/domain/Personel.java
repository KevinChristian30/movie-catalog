package com.nostratech.moviecatalog.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "personels",
	indexes = {
		@Index(name = "uk_personel_name", columnList = "name")
	}
)
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE personels SET deleted = true WHERE id = ?")
public class Personel extends AbstractBaseEntity {
	private static final long serialVersionUID = -8247445750608898830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "bio", nullable = false)
	private String bio;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "gender", nullable = false, length = 6)
	private String gender;
	
	@Column(name = "image_url", nullable = false)
	private String imageUrl;
	
	@OneToMany(mappedBy = "personel")
    private List<MoviePersonelSpecialty> moviePersonelSpecialties;
}
