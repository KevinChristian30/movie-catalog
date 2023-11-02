package com.nostratech.moviecatalog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostratech.moviecatalog.security.filter.JWTAuthProcessingFilter;
import com.nostratech.moviecatalog.security.filter.UsernamePasswordAuthProcessingFilter;
import com.nostratech.moviecatalog.security.handler.UsernamePasswordAuthFailureHandler;
import com.nostratech.moviecatalog.security.handler.UsernamePasswordAuthSuccessHandler;
import com.nostratech.moviecatalog.security.provider.JWTAuthenticationProvider;
import com.nostratech.moviecatalog.security.provider.UsernamePasswordAuthProvider;
import com.nostratech.moviecatalog.security.util.JWTTokenFactory;
import com.nostratech.moviecatalog.security.util.SkipPathRequestMatcher;
import com.nostratech.moviecatalog.security.util.TokenExtractor;
import com.nostratech.moviecatalog.service.UserService;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	private static final String SIGN_UP_URL = "/v1/users";
	private static final String AUTH_URL = "/v1/sign-in";
	private static final String V1_URL = "/v1/**";
	
	private final static List<String> PERMIT_ENDPOINT_LIST = Arrays.asList(AUTH_URL, 
            "/swagger-ui.html"
            , "/swagger-ui/index.html"
            , "/swagger-ui/index.css"
            , "/favicon.ico"
            , "/swagger-ui/swagger-ui.css"
            , "/swagger-ui/swagger-ui.css.map"
            , "/swagger-ui/swagger-ui-standalone-preset.js"
            , "/swagger-ui/swagger-ui-standalone-preset.js.map"
            , "/swagger-ui/swagger-ui-bundle.js"
            , "/swagger-ui/swagger-ui-bundle.js.map"
            , "/swagger-ui/favicon-32x32.png"
            , "/swagger-ui/favicon-16x16.png"
            , "/swagger-ui/swagger-initializer.js"
            , "/v3/api-docs/swagger-config"
            , "/v3/api-docs"
            , SIGN_UP_URL
            , "/v1/guest-tokens"
            );
	private static final List<String> AUTHENTICATE_ENDPOINT_LIST = Arrays.asList(V1_URL);
	
	@Autowired
	UsernamePasswordAuthProvider usernamePasswordAuthProvider;
	
	@Autowired
	JWTAuthenticationProvider jwtAuthenticationProvider;
	
	@Autowired
	UserService userService;
	
	@Bean
	AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(
		ObjectMapper objectMapper,
		JWTTokenFactory factory 
	) {
		return new UsernamePasswordAuthSuccessHandler(objectMapper, factory, userService); 
	}
	
	@Bean
	AuthenticationFailureHandler usernamePasswordAuthFailureHandler(
		ObjectMapper objectMapper
	) {
		return new UsernamePasswordAuthFailureHandler(objectMapper); 
	}
	
	@Bean
	AuthenticationConfiguration authenticationConfiguration() {
		return new AuthenticationConfiguration();
	}
	
	@Bean
	AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter(
		ObjectMapper objectMapper,
		AuthenticationSuccessHandler authenticationSuccessHandler,
		AuthenticationFailureHandler authenticationFailureHandler,
		AuthenticationManager authenticationManager
	) {
		UsernamePasswordAuthProcessingFilter filter = 
			new UsernamePasswordAuthProcessingFilter(
				AUTH_URL, 
				objectMapper, 
				authenticationSuccessHandler, 
				authenticationFailureHandler
			);
		
		filter.setAuthenticationManager(authenticationManager);
		
		return filter;
	}
	
	@Bean
	JWTAuthProcessingFilter jwtAuthProcessingFilter(
		TokenExtractor tokenExtractor,
		AuthenticationFailureHandler authenticationFailureHandler,
		AuthenticationManager authenticationManager
	) {
		SkipPathRequestMatcher matcher = 
			new SkipPathRequestMatcher(
				PERMIT_ENDPOINT_LIST, 
				AUTHENTICATE_ENDPOINT_LIST);
		
		JWTAuthProcessingFilter filter = 
			new JWTAuthProcessingFilter(
				matcher, 
				tokenExtractor, 
				authenticationFailureHandler);
		
		filter.setAuthenticationManager(authenticationManager);
		
		return filter;
	}
	
	@Autowired
	void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(usernamePasswordAuthProvider)
			.authenticationProvider(jwtAuthenticationProvider);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(
		HttpSecurity http, 
		UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter,
		JWTAuthProcessingFilter jwtAuthProcessingFilter
	) throws Exception {
		http
		.authorizeHttpRequests()
        .requestMatchers(PERMIT_ENDPOINT_LIST.toArray(new String[0])).permitAll()
        .requestMatchers("/error").permitAll()
        .requestMatchers(V1_URL).authenticated()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(
    		usernamePasswordAuthProcessingFilter, 
    		UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
    		jwtAuthProcessingFilter, 
    		UsernamePasswordAuthenticationFilter.class);
        
		return http.build();
	}
}
