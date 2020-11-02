package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Group;

@Component
public class GroupValidator extends EntityValidator<Group, Long>{
	
	private static final String NAME_PATTERN = "^[a-z]{2}[-][0-9]{2}$";

	@Override
	public void checkSpecialEntityRequirements(Group group) {

		checkForNullOrEmptyProperty(group.getName());
		checkByPattern(group.getName(), NAME_PATTERN);
	}
}
