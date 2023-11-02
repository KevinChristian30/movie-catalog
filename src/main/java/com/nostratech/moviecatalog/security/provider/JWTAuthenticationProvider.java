package com.nostratech.moviecatalog.security.provider;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nostratech.moviecatalog.security.context.UserContext;
import com.nostratech.moviecatalog.security.model.JWTAuthenticationToken;
import com.nostratech.moviecatalog.security.model.RawAccessJWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
	private final Key key;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessJWTToken token = (RawAccessJWTToken) authentication.getCredentials();
		Jws<Claims> claims = token.parseClaims(key);
		
		String subject = claims.getBody().getSubject();
		List<String> scopes = claims.getBody().get("scopes", List.class);
		List<GrantedAuthority> 
			grantedAuthorities = 
				scopes
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		
		UserDetails userDetails = 
			new UserContext(
				subject, 
				grantedAuthorities,
				claims.getBody().get("theatres", List.class)
			);
		
		return new JWTAuthenticationToken(userDetails, grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JWTAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
