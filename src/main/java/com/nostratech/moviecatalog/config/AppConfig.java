package com.nostratech.moviecatalog.config;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostratech.moviecatalog.security.util.JWTTokenFactory;
import com.nostratech.moviecatalog.seeder.impl.InitialDatabaseSeeder;
import com.nostratech.moviecatalog.service.CityService;
import com.nostratech.moviecatalog.service.GenreService;
import com.nostratech.moviecatalog.service.NeighborhoodService;
import com.nostratech.moviecatalog.service.DistrictService;
import com.nostratech.moviecatalog.service.ProvinceService;
import com.nostratech.moviecatalog.service.RoleService;
import com.nostratech.moviecatalog.service.SpecialtyService;
import com.nostratech.moviecatalog.service.TicketStatusService;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class AppConfig {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private DistrictService postalcodeService;
	
	@Autowired
	private NeighborhoodService neighborhoodService;
	
	@Autowired
	private TicketStatusService ticketStatusService;
	
	@Bean
	InitialDatabaseSeeder seeder() {
		return new InitialDatabaseSeeder(
			roleService, 
			genreService,
			specialtyService,
			provinceService,
			cityService,
			postalcodeService,
			neighborhoodService,
			ticketStatusService
		);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}	
	
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Bean
	Key key(ApplicationProperties applicationProperties) {
		byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getSecretKey());
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	@Bean
	JWTTokenFactory jwtTokenFactory(
		ApplicationProperties applicationProperties,
		Key key
	) {
		return new JWTTokenFactory(applicationProperties, key);
	}
}
