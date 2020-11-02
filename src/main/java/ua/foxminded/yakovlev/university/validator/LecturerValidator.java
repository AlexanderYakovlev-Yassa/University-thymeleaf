package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Lecturer;

@Component
public class LecturerValidator extends EntityValidator<Lecturer, Long> {

	private static final String NAME_PATTERN = "^[A-ZА-Я][a-zа-я]*$";

	@Override
	protected void checkSpecialEntityRequirements(Lecturer entity) {

		checkForNullOrEmptyProperty(entity.getFirstName());
		checkByPattern(entity.getFirstName(), NAME_PATTERN);
		checkForNullOrEmptyProperty(entity.getLastName());
		checkByPattern(entity.getLastName(), NAME_PATTERN);
	}
}
