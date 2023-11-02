package com.nostratech.moviecatalog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "configuration")
@Data
public class ApplicationProperties {
	private int tokenDuration;
	private String secretKey;
	private int reviewEditLimit;
}
