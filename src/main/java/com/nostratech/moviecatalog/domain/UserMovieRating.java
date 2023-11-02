package com.nostratech.moviecatalog.domain;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "user_movie_rating")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE user_movie_rating SET deleted = true WHERE id = ?")
public class UserMovieRating implements Serializable {
	private static final long serialVersionUID = 4292325083220575223L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@Column(name = "rating")
	private Integer rating;
	
	@Column(name = "review")
	private String review;
	
	@Column(name = "deleted")
	private Boolean deleted = false;
}
