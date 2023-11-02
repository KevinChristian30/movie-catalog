package com.nostratech.moviecatalog.domain;

import java.time.LocalDateTime;
import java.util.List;

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

@Entity
@Data
@Table(name = "schedules")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE schedules SET deleted = true WHERE id = ?")
public class Schedule extends AbstractBaseEntity {
	private static final long serialVersionUID = 8350841724804044889L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "studio_id", nullable = false)
	private Studio studio;
	
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startTime;
	
	@Column(name = "end_date", nullable = false)
	private LocalDateTime endTime;
	
	@OneToMany(mappedBy = "schedule")
	private List<Ticket> tickets;
}
