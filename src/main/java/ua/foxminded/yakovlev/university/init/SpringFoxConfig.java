package ua.foxminded.yakovlev.university.init;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	
	private static final String BASE_PACKAGE = "swagger.apis.request-handler-selectors.base-package";
	private static final String TITLE = "swagger.api.info.title";
	private static final String DESCRIPTION = "swagger.api.info.description";
	private static final String VERSION = "swagger.api.info.version";
	private static final String TERMS_OF_SERVICE_URL = "swagger.api.info.terms-of-service-url";
	private static final String LICENSE = "swagger.api.info.license";
	private static final String LICENSE_URL = "swagger.api.info.licenseUrl";
	private static final String CONTACT_NAME = "swagger.api.info.contact.name";
	private static final String CONTACT_URL = "swagger.api.info.contact.url";
	private static final String CONTACT_EMAIL = "swagger.api.info.contact.email";
	
	@Autowired
	private Environment env;
	
    @Bean
    public Docket api() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage(env.getProperty(BASE_PACKAGE)))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiDetail());
    }
    
    private ApiInfo apiDetail() {
    	
    	return new ApiInfo(
    			env.getProperty(TITLE)
    			, env.getProperty(DESCRIPTION)
    			, env.getProperty(VERSION)
    			, env.getProperty(TERMS_OF_SERVICE_URL)
    			, getContact()
    			, env.getProperty(LICENSE)
    			, env.getProperty(LICENSE_URL)
    			, Collections.emptyList()
    			);
    }
    
    private Contact getContact() {
    	
    	return new Contact(
    			env.getProperty(CONTACT_NAME)
    			, env.getProperty(CONTACT_URL)
    			, env.getProperty(CONTACT_EMAIL)
    			);
    }
}