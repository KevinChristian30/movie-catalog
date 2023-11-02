package com.nostratech.moviecatalog.security.util;

public interface TokenExtractor {
	public String extract(String payload);
}
