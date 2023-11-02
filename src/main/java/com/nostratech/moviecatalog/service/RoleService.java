package com.nostratech.moviecatalog.service;

import java.util.List;

import com.nostratech.moviecatalog.domain.Role;

public interface RoleService {
	public Long getRowCount();
	
	public void createRoles(List<Role> roles);
	
	public Role findByName(String name);
}
