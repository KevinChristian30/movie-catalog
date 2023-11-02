package com.nostratech.moviecatalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	public Optional<Movie> findBySecureId(String secureId);
	
	public Page<Movie> findByTitleLikeIgnoreCase(String title, Pageable pageable);

	public Page<Movie> findBySecureIdIn(List<String> secureIdList, Pageable pageable);

	@Query("SELECT m FROM Movie m "
			+ "JOIN m.genres g "
			+ "WHERE g.id = :id")
	public Page<Movie> findMoviesByGenre(Long id, Pageable pageable);
	
	@Query("SELECT m FROM Movie m "
			+ "JOIN m.moviePersonelSpecialties mps "
			+ "WHERE mps.personel.secureId = :personelId")
	public Page<Movie> findMoviesByPersonel(String personelId, Pageable pageable);
	
	@Query("SELECT m FROM Movie m "
			+ "LEFT JOIN UserMovieRating umr "
			+ "ON m.id = umr.movie.id "
			+ "GROUP BY m.id "
			+ "ORDER BY COUNT(umr.movie.id) DESC")
	public Page<Movie> findByReviewCount(Pageable pageable); 
	
	@Query("SELECT m FROM Movie m "
			+ "JOIN UserMovieRating umr "
			+ "ON m.id = umr.movie.id "
			+ "GROUP BY m.id "
			+ "ORDER BY AVG(umr.rating) DESC")
	public Page<Movie> findByRating(Pageable pageable); 
}
