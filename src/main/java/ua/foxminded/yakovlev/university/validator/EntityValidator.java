package ua.foxminded.yakovlev.university.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class EntityValidator<E> {
	
	private static final String EMPTY = "";

	public void check(E entity) {
		
		if (entity == null) {
			throw new IllegalArgumentException("Entity is null");
		}
		
		checkSpetialEntityRequirements(entity);
	}
	
	public void checkId(Long id) {
		
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		
		if (id <= 0) {
			throw new IllegalArgumentException("wrong id");
		}
	}
	
	protected abstract void checkSpetialEntityRequirements(E entity);
	
	protected void checkByPattern(String propertyValue, String propertyPattern) {
		
		Pattern p = Pattern.compile(propertyPattern);
		Matcher m = p.matcher(propertyValue);
		
		if (!m.matches()) {
			throw new IllegalArgumentException("Wrong property format");
		}
	}
	
	protected void checkForNullOrEmptyProperty(String propertyValue) {
		
		if (propertyValue == null || propertyValue == EMPTY) {
			throw new IllegalArgumentException("Null or empty the property value");
		}
	}
}
