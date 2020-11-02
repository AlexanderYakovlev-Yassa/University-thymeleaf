package ua.foxminded.yakovlev.university.validator;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@Component
public class TimetableRecordValidator extends EntityValidator<TimetableRecord, Long>{

	@Override
	protected void checkSpecialEntityRequirements(TimetableRecord entity) {
		
		checkForNullProperty(entity.getDate());		
	}
}
