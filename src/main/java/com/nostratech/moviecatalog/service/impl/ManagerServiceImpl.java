package com.nostratech.moviecatalog.service.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Theatre;
import com.nostratech.moviecatalog.dto.manager.ManagerCreateRequestDTO;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.TheatreRepository;
import com.nostratech.moviecatalog.service.ManagerService;
import com.nostratech.moviecatalog.service.RoleService;
import com.nostratech.moviecatalog.service.UserService;

import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Data
public class ManagerServiceImpl implements ManagerService {
	private final TheatreRepository theatreRepository;
	
	private final UserService userService;
	private final RoleService roleService;
	
	@Override
	@Transactional
	public void createManager(ManagerCreateRequestDTO dto) {
		Theatre theatre = 
			theatreRepository
			.findBySecureId(dto.theatreId())
			.orElseThrow(() -> new NotFoundException("theatre is not found"));
		
		theatre.setUsers(dto.usernames().stream().map(u -> { 
			return userService.findByUsername(u);
		}).collect(Collectors.toList()));
		
		theatreRepository.save(theatre);
	}
}
