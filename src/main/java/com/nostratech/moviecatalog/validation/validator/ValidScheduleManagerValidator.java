package com.nostratech.moviecatalog.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import com.nostratech.moviecatalog.domain.Schedule;
import com.nostratech.moviecatalog.exception.BadRequestException;
import com.nostratech.moviecatalog.exception.NotFoundException;
import com.nostratech.moviecatalog.repository.ScheduleRepository;
import com.nostratech.moviecatalog.service.UserService;
import com.nostratech.moviecatalog.validation.annotation.ValidScheduleManager;

@AllArgsConstructor
public class ValidScheduleManagerValidator implements ConstraintValidator<ValidScheduleManager, String> {
	private final ScheduleRepository scheduleRepository;
	private final UserService userService;
	
	@Override
	public void initialize(ValidScheduleManager constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Schedule schedule = 
			scheduleRepository
			.findBySecureId(value)
			.orElseThrow(() -> new NotFoundException("schedule not found"));
		String theatreId = schedule.getStudio().getTheatre().getSecureId();

		for (String id : userService.getCurrentUserTheatreIds()) {
			if (theatreId.equals(id)) return true;
		}
		
		throw new BadRequestException("invalid access to manage schedule");
	}
}
