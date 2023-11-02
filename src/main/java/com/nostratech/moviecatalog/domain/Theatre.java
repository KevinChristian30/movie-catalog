package com.nostratech.moviecatalog.domain;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "theatres", 
indexes = {
		@Index(name = "uk_theatre_name", columnList = "name")
	}
)
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE theatres SET deleted = true WHERE id = ?")
public class Theatre extends AbstractBaseEntity {
	private static final long serialVersionUID = -7970161447623303556L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "full_address", nullable = false)
	private String fullAddress;

	@ManyToOne
	@JoinColumn(name = "neighborhood_id", nullable = false)
	private Neighborhood neighborhood;

	@OneToMany(mappedBy = "theatre")
	private List<Studio> studios;
	
	@ManyToMany
	@JoinTable(name = "manages",
		joinColumns = @JoinColumn(name = "theatre_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	private List<User> users;
}