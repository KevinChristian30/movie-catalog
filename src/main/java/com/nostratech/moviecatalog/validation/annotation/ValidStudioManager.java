package com.nostratech.moviecatalog.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nostratech.moviecatalog.validation.validator.ValidStudioManagerValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ 
	ElementType.FIELD, 
	ElementType.METHOD, 
	ElementType.PARAMETER, 
	ElementType.ANNOTATION_TYPE, 
	ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStudioManagerValidator.class)
public @interface ValidStudioManager {
	String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
