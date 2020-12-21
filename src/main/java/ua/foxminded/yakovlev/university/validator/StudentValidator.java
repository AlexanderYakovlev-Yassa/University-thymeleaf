package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Person;
import ua.foxminded.yakovlev.university.entity.Student;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {
	
	private final PersonValidator personValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return Student.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Person person = (Person)target;
		personValidator.validate(person, errors);
	}
}
