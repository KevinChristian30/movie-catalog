package com.nostratech.moviecatalog.security.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.nostratech.moviecatalog.config.ApplicationProperties;
import com.nostratech.moviecatalog.security.model.AccessJWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTTokenFactory {
	private final ApplicationProperties applicationProperties;
	private final Key secret;
	
	public AccessJWTToken createAccessJWTToken(
		String username, 
		Collection<? extends GrantedAuthority> authorities,
		List<String> theatreIds
	) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put(
			"scopes", 
			authorities
				.stream()
				.map(a -> a.getAuthority())
				.collect(Collectors.toList()));
		claims.put("theatres", theatreIds);
		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime expiredTime = 
			currentTime.plusMinutes(applicationProperties.getTokenDuration());
		
		Date currentTimeDate = Date.from(currentTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());
		Date expiredTimeDate = Date.from(expiredTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());
		
		String token = Jwts
			.builder()
			.setClaims(claims)
			.setIssuer("http://nostratech.com")
			.setIssuedAt(currentTimeDate)
			.setExpiration(expiredTimeDate)
			.signWith(secret, SignatureAlgorithm.HS256)
			.compact();
		
		return new AccessJWTToken(token, claims);
	}
}
