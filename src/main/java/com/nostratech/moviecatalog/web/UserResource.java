package com.nostratech.moviecatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.dto.user.UserCreateRequestDTO;
import com.nostratech.moviecatalog.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserResource {
	private final UserService userService;
	
	@PostMapping("/v1/users")
	public ResponseEntity<Void> create(
		@RequestBody
		@Valid
		UserCreateRequestDTO dto
	) {
		userService.signUp(dto);
		
		return ResponseEntity.created(URI.create("/users")).build();
	}
}
