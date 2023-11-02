package com.nostratech.moviecatalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Genre;
import com.nostratech.moviecatalog.dto.genre.GenreResponseDTO;
import com.nostratech.moviecatalog.dto.util.ResponsePageDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.GenreRepository;
import com.nostratech.moviecatalog.service.GenreService;
import com.nostratech.moviecatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
	private final GenreRepository genreRepository;
	
	@Override
	public Long getRowCount() {
		return genreRepository.count();
	}

	@Override
	public void createGenres(List<Genre> genres) {
		genreRepository.saveAll(genres);
	}
	
	@Override
	public Genre findById(Long id) {
		return genreRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("genre not found"));
	}

	@Override
	public Genre findByName(String name) {
		return genreRepository
				.findByName(name)
				.orElseThrow(() -> new NotFoundException("genre not found"));
	}

	@Override
	public ResponsePageDTO<GenreResponseDTO> findGenresByName(Integer page, Integer limit, String name) {
		Pageable pageable = PageRequest.of(page, limit);
		
		String query = "%" + name + "%";
		Page<Genre> result = genreRepository.findByNameLikeIgnoreCase(query, pageable);
		
		List<GenreResponseDTO> dtos = result.stream().map(p -> {
			return new GenreResponseDTO(p.getName());
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, result.getTotalElements(), result.getTotalPages());
	}
}
