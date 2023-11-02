package com.nostratech.moviecatalog.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostratech.moviecatalog.domain.User;
import com.nostratech.moviecatalog.security.model.AccessJWTToken;
import com.nostratech.moviecatalog.security.util.JWTTokenFactory;
import com.nostratech.moviecatalog.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsernamePasswordAuthSuccessHandler implements AuthenticationSuccessHandler {
	private final ObjectMapper objectMapper;
	private final JWTTokenFactory jwtTokenFactory;
	private final UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		User user = userService.findByUsername(userDetails.getUsername());
		List<String> theatreIds = 
			user.getTheatres().stream().map(
				t -> t.getSecureId()
			).collect(Collectors.toList());
		
		AccessJWTToken token = jwtTokenFactory.createAccessJWTToken(
			userDetails.getUsername(), 
			userDetails.getAuthorities(),
			theatreIds
		);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("token", token.getToken());
		resultMap.put("username", userDetails.getUsername());
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), resultMap);
	}
}
