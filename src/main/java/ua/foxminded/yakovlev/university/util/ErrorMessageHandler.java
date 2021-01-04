package ua.foxminded.yakovlev.university.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ErrorMessageHandler {	

	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;

	public String handle(BindingResult bindingResult) {
		
		return String.join("\n", bindingResult.getAllErrors().stream().
		map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
		.toArray(String[]::new));		
	}
}
