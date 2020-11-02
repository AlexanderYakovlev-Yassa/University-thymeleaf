package ua.foxminded.yakovlev.university.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class EntityValidator<E, ID> {

	private static final String EMPTY = "";

	public void check(E entity) {

		if (entity == null) {
			throw new IllegalArgumentException("Entity is null");
		}

		checkSpecialEntityRequirements(entity);
	}

	public void checkId(ID id) {

		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}

		if (id.getClass().equals(Long.class) && (Long)id < 0) {			
				throw new IllegalArgumentException("wrong id");			
		}
		
		if (id.getClass().equals(Integer.class) && (Integer)id < 0) {			
			throw new IllegalArgumentException("wrong id");	
		}			
	}

	protected abstract void checkSpecialEntityRequirements(E entity);

	protected void checkByPattern(String propertyValue, String propertyPattern) {

		Pattern p = Pattern.compile(propertyPattern);
		Matcher m = p.matcher(propertyValue);

		if (!m.matches()) {
			throw new IllegalArgumentException("Wrong property format: pattern[" + propertyPattern + " value [" + propertyValue + "]");
		}
	}
	
	protected void checkForNullOrEmptyProperty(String propertyValue) {
		
		checkForNullProperty(propertyValue);
		checkForEmptyStringProperty(propertyValue);
	}

	protected void checkForNullProperty(Object propertyValue) {

		if (propertyValue == null) {
			throw new IllegalArgumentException("The null property value");
		}
	}
	
	protected void checkForEmptyStringProperty(String propertyValue) {
		if (EMPTY.equals(propertyValue)) {
			throw new IllegalArgumentException("The empty property string value");
		}
	}
}
