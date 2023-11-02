package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Movie;
import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.dto.movie.MovieResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistCreateRequestDTO;
import com.nostratech.moviecatalog.dto.wishlist.WishlistDeleteRequestDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.MovieRepository;
import com.nostratech.moviecatalog.repository.UserRepository;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.service.WishlistService;
import com.nostratech.moviecatalog.util.MovieUtil;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {
	private final MovieRepository movieRepository;
	private final UserRepository userRepository;

	private final UserService userService;
	
	@Override
	public void addToWishlist(WishlistCreateRequestDTO dto) {
		User user = userService.getCurrentUser();
		
		Movie movie = movieRepository
			.findBySecureId(dto.movieId())
			.orElseThrow(() -> new NotFoundException("movie not found"));
				
		if (!user.getWishlist().contains(movie)) {
			user.getWishlist().add(movie);
		}
		
		userRepository.save(user);
	}
	
	@Override
	public ResponsePageDTO<MovieResponseDTO> findWishlist(
		Integer page, 
		Integer limit, 
		String title) {
		Pageable pageable = PageRequest.of(page, limit);
		
		User user = userService.getCurrentUser();
		List<String> secureIds = user
			.getWishlist()
			.stream()
			.map(m -> m.getSecureId())
			.collect(Collectors.toList());
		
		Page<Movie> result = movieRepository.findBySecureIdIn(secureIds, pageable);
		
		List<MovieResponseDTO> dtos = result.stream().map(p -> {
			return MovieUtil.movieToResponseDTO(p);
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}

	@Override
	public void deleteFromWishlist(WishlistDeleteRequestDTO dto) {
		User user = userService.getCurrentUser();
		for (Movie movie : user.getWishlist()) {
			if (movie.getSecureId().equals(dto.movieId())) {
				user.getWishlist().remove(movie);
				
				userRepository.save(user);
				return;
			}
		}
		
		throw new NotFoundException("movie not found");
	}
}
