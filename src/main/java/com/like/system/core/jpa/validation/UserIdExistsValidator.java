package com.like.system.core.jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.like.system.user.service.SystemUserService;

@Component
public class UserIdExistsValidator implements ConstraintValidator<UserIdExists, String> {
	
	private SystemUserService userService;
	
	public UserIdExistsValidator(SystemUserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void initialize(UserIdExists constraintAnnotation) {	
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !userService.CheckDuplicationUser(value);
	}

	
}
