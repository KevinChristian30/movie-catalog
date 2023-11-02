package com.nostratech.moviecatalog.security.model;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class RawAccessJWTToken implements Token {
	private String token;
	
	public RawAccessJWTToken(String token) {
		super();
		this.token = token;
	}
	
	public Jws<Claims> parseClaims(Key signingKey) {
		return Jwts
				.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token);
	}

	@Override
	public String getToken() {
		return token;
	}
}
