package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Person;

@Component
@RequiredArgsConstructor
public class LecturerValidator implements Validator {
	
	private final PersonValidator personValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return Lecturer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Person person = (Person)target;
		
		personValidator.validate(person, errors);
		
		ValidationUtils.rejectIfEmpty(errors, "position", "validator.message.empty_position");
	}
}
