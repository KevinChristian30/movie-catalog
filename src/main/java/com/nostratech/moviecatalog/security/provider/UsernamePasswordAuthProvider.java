package com.nostratech.moviecatalog.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nostratech.moviecatalog.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthProvider implements AuthenticationProvider {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		UserDetails userDetails = (UserDetails) userService.loadUserByUsername(username);
		
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid credentials");
		}
		
		return 
			new UsernamePasswordAuthenticationToken(
				userDetails, 
				null, 
				userDetails.getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return 
			(UsernamePasswordAuthenticationToken
				.class
				.isAssignableFrom(authentication));
	}
}
