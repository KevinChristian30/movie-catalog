package com.nostratech.moviecatalog.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.dto.user.UserCreateRequestDTO;

@Service
public interface UserService extends UserDetailsService {
	public User findByUsername(String username);
	
	public User getCurrentUser();
	
	public void signUp(UserCreateRequestDTO dto);
	
	public List<String> getCurrentUserTheatreIds();
}
