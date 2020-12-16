package ua.foxminded.yakovlev.university.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.foxminded.yakovlev.university.entity.Group;

@Component
public class GroupValidator implements Validator {

	@Value(value = "${group.name.pattern}")
	private String namePattern;

	@Override
	public boolean supports(Class<?> clazz) {
		return Group.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Group group = (Group) target;
		Pattern pattern = Pattern.compile(namePattern);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validator.message.empty_group_name");

		if (errors.getFieldErrorCount("name") == 0) {
			if (!pattern.matcher(group.getName()).matches()) {
				errors.rejectValue("name", "validator.message.name_pattern_not_matches");
			}
		}
	}

}
