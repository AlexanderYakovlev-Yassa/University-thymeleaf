package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.foxminded.yakovlev.university.entity.Course;

@Component
public class CourseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Course.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Course course = (Course) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validator.message.empty_course_name");

		if (course.getDescription().length() > 1024) {
			errors.rejectValue("description", "validator.message.course_description_too_long");
		}
	}
}
