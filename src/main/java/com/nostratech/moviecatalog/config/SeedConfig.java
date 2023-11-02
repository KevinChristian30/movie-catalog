package com.nostratech.moviecatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.nostratech.moviecatalog.seeder.Seeder;

@Configuration
public class SeedConfig {	
	@Autowired
	private Seeder seeder;
	
	@EventListener
	public void seed(ContextRefreshedEvent event) {
	    seeder.seed();
	}
}
