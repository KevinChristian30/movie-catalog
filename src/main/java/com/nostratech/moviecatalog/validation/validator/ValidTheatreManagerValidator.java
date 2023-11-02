package com.nostratech.moviecatalog.validation.validator;

import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.validation.annotation.ValidTheatreManager;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidTheatreManagerValidator implements ConstraintValidator<ValidTheatreManager, String> {
	private final UserService userService;
	
	@Override
	public void initialize(ValidTheatreManager constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (String theatreId : userService.getCurrentUserTheatreIds()) {
			if (value.equals(theatreId)) return true;
		}
		
		throw new BadRequestException("invalid theatre id");
	}
}
