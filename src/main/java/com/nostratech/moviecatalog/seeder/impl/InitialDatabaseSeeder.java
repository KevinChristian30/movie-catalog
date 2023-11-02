package com.nostratech.moviecatalog.seeder.impl;

import java.util.ArrayList;
import java.util.List;

import com.nostratech.moviecatalog.domain.City;
import com.nostratech.moviecatalog.domain.Genre;
import com.nostratech.moviecatalog.domain.Neighborhood;
import com.nostratech.moviecatalog.domain.District;
import com.nostratech.moviecatalog.domain.Province;
import com.nostratech.moviecatalog.domain.Role;
import com.nostratech.moviecatalog.domain.Specialty;
import com.nostratech.moviecatalog.domain.TicketStatus;
import com.nostratech.moviecatalog.seeder.Seeder;
import com.nostratech.moviecatalog.service.CityService;
import com.nostratech.moviecatalog.service.GenreService;
import com.nostratech.moviecatalog.service.NeighborhoodService;
import com.nostratech.moviecatalog.service.DistrictService;
import com.nostratech.moviecatalog.service.ProvinceService;
import com.nostratech.moviecatalog.service.RoleService;
import com.nostratech.moviecatalog.service.SpecialtyService;
import com.nostratech.moviecatalog.service.TicketStatusService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class InitialDatabaseSeeder implements Seeder {
	private final RoleService roleService;
	private final GenreService genreService;
	private final SpecialtyService specialtyService;
	private final ProvinceService provinceService;
	private final CityService cityService;
	private final DistrictService districtService;
	private final NeighborhoodService neighborhoodService;
	private final TicketStatusService ticketStatusService;
	
	public void seed() {
		seedRolesTable();
		seedGenresTable();
		seedSpecialtiesTable();
		seedProvincesTable();
		seedCitiesTable();
		seedDistricts();
		seedNeighborhoods();
		seedTicketStatuses();
	}
	
	private void seedRolesTable() {
		Long count = roleService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `roles` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(1L, "ADMIN"));
		roles.add(new Role(2L, "USER"));

		roleService.createRoles(roles);
		log.info("SEEDER: `roles` seeded successfully, added {} rows", roles.size());
	}
	
	private void seedGenresTable() {
		Long count = genreService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `genres` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Genre> genres = new ArrayList<>();
		genres.add(new Genre(1L, "Action"));
		genres.add(new Genre(2L, "Comedy"));
		genres.add(new Genre(3L, "Drama"));
		genres.add(new Genre(4L, "Horror"));
		genres.add(new Genre(5L, "Mystery"));
		genres.add(new Genre(6L, "Documentary"));
		genres.add(new Genre(7L, "Thriller"));
		genres.add(new Genre(8L, "Biography"));
		genres.add(new Genre(9L, "Musical"));
		genres.add(new Genre(10L, "Adventure"));

		genreService.createGenres(genres);
		log.info("SEEDER: `genres` seeded successfully, added {} rows", genres.size());
	}

	private void seedSpecialtiesTable() {
		Long count = specialtyService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `specialties` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Specialty> specialties = new ArrayList<>();
		specialties.add(new Specialty(1L, "Director"));
		specialties.add(new Specialty(2L, "Writer"));
		specialties.add(new Specialty(3L, "Producer"));
		specialties.add(new Specialty(4L, "Cameraman"));
		specialties.add(new Specialty(5L, "Sound Engineer"));
		specialties.add(new Specialty(6L, "Lights"));
		specialties.add(new Specialty(7L, "Visual Effects"));
		specialties.add(new Specialty(8L, "Actor"));

		specialtyService.createSpecialties(specialties);
		log.info("SEEDER: `specialties` seeded successfully, added {} rows", specialties.size());
	}
	
	
	private void seedProvincesTable() {
		Long count = provinceService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `provinces` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Province> provinces = new ArrayList<>();
		provinces.add(new Province(1L, "DKI Jakarta"));

		provinceService.createProvinces(provinces);
		log.info("SEEDER: `provinces` seeded successfully, added {} rows", provinces.size());
	}
	
	private void seedCitiesTable() {
		Long count = cityService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `cities` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Province> provinces = provinceService.getAll();
		List<City> cities = new ArrayList<>();
		cities.add(new City(1L, "Jakarta Pusat", provinces.get(0)));
		cities.add(new City(2L, "Jakarta Timur", provinces.get(0)));

		cityService.createCities(cities);
		log.info("SEEDER: `cities` seeded successfully, added {} rows", cities.size());
	}
	
	private void seedDistricts() {
		Long count = districtService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `districts` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<District> districts = new ArrayList<>();
		List<City> cities = cityService.getAll();
		
		districts.add(new District(1L, "Gambir", cities.get(0)));
		districts.add(new District(2L, "Tanah Abang", cities.get(0)));
		
		districts.add(new District(3L, "Matraman", cities.get(1)));
		districts.add(new District(4L, "Pulo Gadung", cities.get(1)));
		
		districtService.createDistricts(districts);
		log.info("SEEDER: `districts` seeded successfully, added {} rows", cities.size());
	}
	
	private void seedNeighborhoods() {
		Long count = neighborhoodService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `neigborhoods` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<Neighborhood> neighborhoods= new ArrayList<>();
		List<District> districts = districtService.getAll();

		neighborhoods.add(new Neighborhood(1L, "Kebon Kelapa", "10120", districts.get(0)));
		neighborhoods.add(new Neighborhood(2L, "Duri Pulo", "10140", districts.get(0)));
		
		neighborhoods.add(new Neighborhood(3L, "Bendungan Hilir", "10210", districts.get(1)));
		neighborhoods.add(new Neighborhood(4L, "Karet Tengsin", "10220", districts.get(1)));
		
		neighborhoods.add(new Neighborhood(5L, "Pisangan Baru", "13110", districts.get(2)));
		neighborhoods.add(new Neighborhood(6L, "Kayu Manis", "13130", districts.get(2)));
		
		neighborhoods.add(new Neighborhood(7L, "Kayu Putih", "13210", districts.get(3)));
		neighborhoods.add(new Neighborhood(8L, "Jati", "13220", districts.get(3)));
		
		neighborhoodService.createNeighborhoods(neighborhoods);
		log.info("SEEDER: `neigborhoods` seeded successfully, added {} rows", neighborhoods.size());
	}
	
	private void seedTicketStatuses() {
		Long count = ticketStatusService.getRowCount();
		if (count != 0) {
			log.info("SEEDER: `ticket_statuses` table already has {} rows, seeding is not needed", count);
			return;
		}
		
		List<TicketStatus> ticketStatuses = new ArrayList<>();
	
		ticketStatuses.add(new TicketStatus(1L, "Available"));
		ticketStatuses.add(new TicketStatus(2L, "Sold"));
		
		ticketStatusService.createTicketStatuses(ticketStatuses);
		log.info("SEEDER: `ticket_statuses` seeded successfully, added {} rows", ticketStatuses.size());
	}
}
