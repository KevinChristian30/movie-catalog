package com.nostratech.moviecatalog.util;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.nostratech.moviecatalog.security.util.TokenExtractor;

import io.micrometer.common.util.StringUtils;

@Component
public class JWTHeaderExtractor implements TokenExtractor {
	private static final String HEADER_PREFIX = "Bearer ";
	
	@Override
	public String extract(String payload) {
		if (StringUtils.isBlank(payload)) {
			throw new AuthenticationServiceException("authorization header must exist");
		}
		
		if (payload.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("invalid authorization header");
		}
		
		return payload.substring(HEADER_PREFIX.length(), payload.length());
	}
}
