package com.nostratech.moviecatalog.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostratech.moviecatalog.dto.authentication.LoginRequestDTO;
import com.nostratech.moviecatalog.exception.BadRequestException;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsernamePasswordAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private final ObjectMapper objectMapper;
	
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	public UsernamePasswordAuthProcessingFilter(
		String defaultFilterProcessesUrl, 
		ObjectMapper objectMapper,
		AuthenticationSuccessHandler authenticationSuccessHandler, 
		AuthenticationFailureHandler authenticationFailureHandler
	) {
		super(defaultFilterProcessesUrl);
		this.objectMapper = objectMapper;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		LoginRequestDTO dto = 
			objectMapper
			.readValue(request.getReader(), LoginRequestDTO.class);
		
		if (StringUtils.isBlank(dto.username()) || StringUtils.isBlank(dto.password())) {
			throw new BadRequestException("username and password must be filled");
		}
		
		UsernamePasswordAuthenticationToken token = 
			new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
}
