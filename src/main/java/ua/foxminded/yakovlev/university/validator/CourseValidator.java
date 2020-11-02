package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Course;

@Component
public class CourseValidator extends EntityValidator<Course, Long> {

	@Override
	protected void checkSpecialEntityRequirements(Course entity) {
		
		checkForNullOrEmptyProperty(entity.getName());
	}
}
