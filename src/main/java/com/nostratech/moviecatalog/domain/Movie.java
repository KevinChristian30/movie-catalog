package com.nostratech.moviecatalog.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movies", 
	indexes = {
		@Index(name = "uk_title", columnList = "title")
	}
)
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE movies SET deleted = true WHERE id = ?")
public class Movie extends AbstractBaseEntity {
	private static final long serialVersionUID = 3063646939360018737L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "release_date", nullable = false)
	private LocalDate releaseDate;
	
	@Column(name = "description")
	private String description;

    @Column(name = "duration_in_minutes", nullable = false)
	private Long durationInMinutes;
    
	@Column(name = "image_url", nullable = false)
	private String imageUrl;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "movie_genre",
		joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
	)
	private List<Genre> genres;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MoviePersonelSpecialty> moviePersonelSpecialties;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<UserMovieRating> userMovieRatings = new ArrayList<>();
	
	@ManyToMany(mappedBy = "wishlist")
	private List<User> users;
	
	@OneToMany(mappedBy = "movie")
	private List<Schedule> schedules;
}