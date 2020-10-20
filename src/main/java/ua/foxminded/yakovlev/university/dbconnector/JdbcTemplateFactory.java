package ua.foxminded.yakovlev.university.dbconnector;

import java.util.ResourceBundle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateFactory {

	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";

	private final String dbProperties;
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateFactory(String dbProperties) {
		this.dbProperties = dbProperties;
	}

	public JdbcTemplate getJdbcTemplate() {

		if (jdbcTemplate == null) {
			ResourceBundle dbResourceBundle = ResourceBundle.getBundle(dbProperties);

			DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(
					dbResourceBundle.getString(URL), dbResourceBundle.getString(USER),
					dbResourceBundle.getString(PASSWORD));
			driverManagerDataSource.setDriverClassName(dbResourceBundle.getString(DRIVER));

			this.jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
		}

		return jdbcTemplate;
	}
}
