package ua.foxminded.yakovlev.university.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

	@Bean("messageSource")
	public ResourceBundleMessageSource getMessageSource() {
		
		String message = "message";
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(message);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;		
	}
}
