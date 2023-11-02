package com.nostratech.moviecatalog.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Genre;
import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.MoviePersonelSpecialty;
import com.nostratech.moviecatalog.dto.movie.MovieCreateRequestDTO;
import com.nostratech.moviecatalog.dto.movie.MovieDetailResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.movie.MovieUpdateRequestDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.MovieRepository;
import com.nostratech.moviecatalog.service.GenreService;
import com.nostratech.moviecatalog.service.MoviePersonelSpecialtyService;
import com.nostratech.moviecatalog.service.MovieService;
import com.nostratech.moviecatalog.service.PersonelService;
import com.nostratech.moviecatalog.service.SpecialtyService;
import com.nostratech.moviecatalog.util.MovieUtil;
import com.nostratech.moviecatalog.util.PaginationUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
	private final MovieRepository movieRepository;
	
	private final GenreService genreService;
	private final PersonelService personelService;
	private final SpecialtyService specialtyService;
	private final MoviePersonelSpecialtyService moviePersonelSpecialtyService;
	
	@Override
	public void createMovie(MovieCreateRequestDTO dto) {
		Movie movie = new Movie();
		
		movie.setTitle(dto.title());
		
		ZoneId zoneId = ZoneId.of("Asia/Jakarta");
		LocalDate date = Instant.ofEpochSecond(dto.releaseDate()).atZone(zoneId).toLocalDate();
		movie.setReleaseDate(date);
		
		movie.setDescription(dto.description());
		movie.setDurationInMinutes(dto.durationInMinutes());
		movie.setImageUrl(dto.imageUrl());
		
		List<Genre> genres = dto.genres().stream().map(e -> {
			return genreService.findById(e.id());
		}).collect(Collectors.toList());
		movie.setGenres(genres);

		Movie insertedMovie = movieRepository.save(movie);
		
		List<MoviePersonelSpecialty> moviePersonelSpecialtiesList = dto.moviePersonelSpecialties().stream().map(mps -> {
			MoviePersonelSpecialty moviePersonelSpecialty = new MoviePersonelSpecialty();
			
			moviePersonelSpecialty.setMovie(movie);
			moviePersonelSpecialty
				.setPersonel(
					personelService
						.findById(mps.personelId()));
			moviePersonelSpecialty
				.setSpecialty(
					specialtyService
						.findById(mps.specialtyId()));
			moviePersonelSpecialty.setName(mps.name());
			moviePersonelSpecialty.setBio(mps.bio());
			
			return moviePersonelSpecialty;
		}).collect(Collectors.toList());
		insertedMovie.setMoviePersonelSpecialties(moviePersonelSpecialtiesList);
		
		movieRepository.save(insertedMovie);
	}
	
	@Override
	public MovieDetailResponseDTO findMovieById(String secureId) {
		Movie movie = movieRepository
				.findBySecureId(secureId)
				.orElseThrow(() -> new NotFoundException("movie not found"));
		
		return MovieUtil.movieToDetailResponseDTO(movie);
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findMovies(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction,
		String title
	) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Movie> result = movieRepository.findByTitleLikeIgnoreCase("%" + title + "%", pageable);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findMoviesByPersonel(Integer page, Integer limit, String sortBy,
			String direction, String personelId) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Movie> result = movieRepository
			.findMoviesByPersonel(
				personelId,
				pageable
			);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findMoviesByGenre(
		Integer page, 
		Integer limit, 
		String sortBy, 
		String direction,
		String title,
		Long genreId
	) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Movie> result = movieRepository.findMoviesByGenre(genreId, pageable);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findMoviesByReviewCount(Integer page, Integer limit) {
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<Movie> result = movieRepository.findByReviewCount(pageable);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findMoviesByRating(Integer page, Integer limit) {
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<Movie> result = movieRepository.findByRating(pageable);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	@Override
	@Transactional
	public void updateMovie(String secureId, MovieUpdateRequestDTO dto) {
		Movie movie = movieRepository
			.findBySecureId(secureId)
			.orElseThrow(() -> new NotFoundException("movie not found"));
		
		movie.setTitle(dto.title() == null ? movie.getTitle() : dto.title());
		
		if (dto.releaseDate() != null) {
			ZoneId zoneId = ZoneId.of("Asia/Jakarta");
			LocalDate date = Instant.ofEpochSecond(dto.releaseDate()).atZone(zoneId).toLocalDate();
			movie.setReleaseDate(date);
		}
		
		movie.setDescription(
			dto.description() == null ? 
				movie.getDescription() : dto.description());
		movie.setImageUrl(
			dto.imageUrl() == null ? 
				movie.getImageUrl() : dto.imageUrl());
		movie.setDurationInMinutes(
			dto.durationInMinutes() == null ?
				movie.getDurationInMinutes() : dto.durationInMinutes());
		
		if (dto.genres() != null) {
			List<Genre> genres = dto.genres().stream().map(e -> {
				return genreService.findById(e.id());
			}).collect(Collectors.toList());
			movie.setGenres(genres);
		}
		
		if (dto.moviePersonelSpecialties() != null) {
			List<String> secureIds = movie.getMoviePersonelSpecialties().stream().map(mps -> {
				return mps.getSecureId();
			}).collect(Collectors.toList());
			moviePersonelSpecialtyService.deleteBySecureIdList(secureIds);
			
			List<MoviePersonelSpecialty> moviePersonelSpecialtiesList = dto.moviePersonelSpecialties().stream().map(mps -> {
				MoviePersonelSpecialty moviePersonelSpecialty = new MoviePersonelSpecialty();
				
				moviePersonelSpecialty.setMovie(movie);
				moviePersonelSpecialty
					.setPersonel(
						personelService
							.findById(mps.personelId()));
				moviePersonelSpecialty
					.setSpecialty(
						specialtyService
							.findById(mps.specialtyId()));
				moviePersonelSpecialty.setName(mps.name());
				moviePersonelSpecialty.setBio(mps.bio());
				
				return moviePersonelSpecialty;
			}).collect(Collectors.toList());
			
			movie.setMoviePersonelSpecialties(moviePersonelSpecialtiesList);
		}
		
		movieRepository.save(movie);
	}

	@Override
	public void deleteMovie(String secureId) {
		Movie movie = movieRepository
				.findBySecureId(secureId)
				.orElseThrow(() -> new NotFoundException("movie not found"));
		
		movieRepository.delete(movie);
	}
}
