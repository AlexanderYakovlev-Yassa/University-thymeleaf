package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.foxminded.yakovlev.university.entity.Person;

@Component
public class PersonValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validator.message.empty_first_name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "validator.message.empty_last_name");
	}
}
