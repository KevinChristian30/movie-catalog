package com.nostratech.moviecatalog.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nostratech.moviecatalog.domain.Personel;

public interface PersonelRepository extends JpaRepository<Personel, Long>{
	public Optional<Personel> findById(Long id);
	
	public Optional<Personel> findBySecureId(String secureId);
	
	public Page<Personel> findByNameLikeIgnoreCase(String name, Pageable pageable);

	@Query("SELECT p FROM Personel p "
			+ "WHERE p.name ILIKE :name "
			+ "AND p.dateOfBirth = :dateOfBirth")
	public Page<Personel> findByNameAndDateOfBirth(
		String name, LocalDate dateOfBirth, Pageable pageable);
}
