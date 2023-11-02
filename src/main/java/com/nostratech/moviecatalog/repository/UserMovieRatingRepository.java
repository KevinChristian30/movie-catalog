package com.nostratech.moviecatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.domain.UserMovieRating;

import java.util.List;
import java.util.Optional;


public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {
	public Page<UserMovieRating> findByMovie(Movie movie, Pageable pageable);

	public Page<UserMovieRating> findByUser(User user, Pageable pageable);
	
	@Query("SELECT umr FROM UserMovieRating umr "
			+ "WHERE umr.movie.id = :movieId "
			+ "AND umr.user.id = :userId")
	public Optional<UserMovieRating> findByMovieIdAndUserId(Long movieId, Long userId);

	@Query(value = "SELECT * "
			+ "FROM user_movie_rating umr "
			+ "WHERE umr.movie_id = :movieId "
			+ "AND umr.user_id = :userId ", nativeQuery = true)
	public List<UserMovieRating> getMovieRatingsDeletedAndNotDeleted(Long movieId, Long userId);
}
