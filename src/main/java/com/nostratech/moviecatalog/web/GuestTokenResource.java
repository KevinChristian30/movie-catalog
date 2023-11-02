package com.nostratech.moviecatalog.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nostratech.moviecatalog.security.model.AccessJWTToken;
import com.nostratech.moviecatalog.security.util.JWTTokenFactory;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GuestTokenResource {
	private final JWTTokenFactory jwtTokenFactory;
	
	@GetMapping("/v1/guest-tokens")
	public ResponseEntity<Map<String, String>> getGuestToken() {
		AccessJWTToken token = jwtTokenFactory.createAccessJWTToken(
			"guest",
			new ArrayList<GrantedAuthority>(),
			new ArrayList<String>()
		);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("token", token.getToken());
		
		return ResponseEntity.ok(resultMap);
	}
}
