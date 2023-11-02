package com.nostratech.moviecatalog.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "studios")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE studios SET deleted = true WHERE id = ?")
public class Studio extends AbstractBaseEntity {
	private static final long serialVersionUID = -2997767690943282986L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "theatre_id", nullable = false)
	private Theatre theatre;
	
	@OneToMany(mappedBy = "studio")
	private List<Schedule> schedules;
	
	@ManyToOne
	@JoinColumn(name = "studio_type_id", nullable = false)
	private StudioType studioType;
}
