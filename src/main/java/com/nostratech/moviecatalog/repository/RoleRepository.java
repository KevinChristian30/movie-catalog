package com.nostratech.moviecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nostratech.moviecatalog.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Optional<Role> findByName(String name);
}
