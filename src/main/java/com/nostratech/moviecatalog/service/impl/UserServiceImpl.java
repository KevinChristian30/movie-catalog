package com.nostratech.moviecatalog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.Role;
import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.dto.user.UserCreateRequestDTO;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.repository.UserRepository;
import com.nostratech.moviecatalog.security.context.UserContext;
import com.nostratech.moviecatalog.service.RoleService;
import com.nostratech.moviecatalog.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final RoleService roleService;
	
	@Override
	public User findByUsername(String username){
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> {
					return new BadRequestException("Invalid Credentials");
				});
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> {
					return new BadRequestException("Invalid Credentials");
				});
	}

	@Override
	public void signUp(UserCreateRequestDTO dto) {
		if (!dto.password().equals(dto.confirmPassword())) {
			throw new BadRequestException("passwords doesn't match");
		}
		
		User oldUser = userRepository
			.findByUsername(dto.username())
			.orElse(null);
		if (oldUser != null) {
			throw new BadRequestException("username already exists");
		}
		
		User user = new User();
		user.setUsername(dto.username());
		user.setPassword(passwordEncoder.encode(dto.password()));
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByName("USER"));
		user.setRoles(roles);
		
		userRepository.save(user);
	}

	@Override
	public User getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
		
		return userRepository
				.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> {
					return new BadRequestException("Invalid Credentials");
				});
	}

	@Override
	public List<String> getCurrentUserTheatreIds() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserContext userDetails = (UserContext) 
			securityContext
			.getAuthentication()
			.getPrincipal();
		
		return userDetails.getTheatreIds();
	}
}
