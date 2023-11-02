package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username); 
	
	public Optional<User> findById(Long id);
}
