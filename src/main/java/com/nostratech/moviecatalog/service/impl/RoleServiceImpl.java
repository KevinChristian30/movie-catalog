package com.nostratech.moviecatalog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Role;
import com.nostratech.moviecatalog.exception.InternalServerException;
import com.nostratech.moviecatalog.repository.RoleRepository;
import com.nostratech.moviecatalog.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;
	
	@Override
	public Long getRowCount() {
		return roleRepository.count();
	}

	@Override
	public void createRoles(List<Role> roles) {
		roleRepository.saveAll(roles);
	}

	@Override
	public Role findByName(String name) {
		return roleRepository
				.findByName(name)
				.orElseThrow(() -> new InternalServerException("role doesn' exist"));
	}
}
