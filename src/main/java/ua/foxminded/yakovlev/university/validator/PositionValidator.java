package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Position;

@Component
public class PositionValidator extends EntityValidator<Position, Long>{

	@Override
	protected void checkSpecialEntityRequirements(Position entity) {
		
		checkForNullOrEmptyProperty(entity.getName());
	}
}
