package com.nostratech.moviecatalog.validation.validator;

import com.nostratech.moviecatalog.domain.Studio;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.StudioRepository;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.validation.annotation.ValidStudioManager;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidStudioManagerValidator implements ConstraintValidator<ValidStudioManager, String> {
	private final UserService userService;
	private final StudioRepository studioRepository;
	
	@Override
	public void initialize(ValidStudioManager constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Studio studio = 
			studioRepository
			.findBySecureId(value)
			.orElseThrow(() -> new NotFoundException("studio not found"));
		String theatreId = studio.getTheatre().getSecureId();

		for (String id : userService.getCurrentUserTheatreIds()) {
			if (theatreId.equals(id)) return true;
		}
		
		throw new BadRequestException("invalid access to manage theatre");
	}
}
