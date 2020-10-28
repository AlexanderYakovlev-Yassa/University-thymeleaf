package ua.foxminded.yakovlev.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

public class ScriptExecutor {

	private final JdbcTemplate jdbcTemplate;
	
	public ScriptExecutor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void execute(String script) {
		
		jdbcTemplate.execute(script);
	}
}
