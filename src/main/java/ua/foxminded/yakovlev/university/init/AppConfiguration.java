package ua.foxminded.yakovlev.university.init;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ua.foxminded.yakovlev.university")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ua.foxminded.yakovlev.university"})
public class AppConfiguration {

	@Bean
	public DataSource getDataSource() throws NamingException {

		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/UniversityDB");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(getDataSource());
		entityManager.setPackagesToScan(new String[] { "ua.foxminded.yakovlev.university.entity" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		entityManager.setJpaProperties(additionalProperties());

		return entityManager;
	}

	@Bean
	public JpaTransactionManager transactionManager() throws NamingException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("app_config");		
		Properties properties = new Properties();		
		properties.setProperty("hibernate.dialect", resourceBundle.getString("hibernate.dialect"));
		return properties;
	}
}
