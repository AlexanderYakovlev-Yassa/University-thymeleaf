package ua.foxminded.yakovlev.university.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractDao<E, ID> implements EntityDao<E, ID> {

	private final String findAll;
	private final String findById;
	private final String update;
	private final String delete;
	private final RowMapper<E> rowMapper;
	private final JdbcTemplate jdbcTemplate;

	public AbstractDao(JdbcTemplate jdbcTemplate, 
			RowMapper<E> rowMapper, 
			String findAll, 
			String findById, 
			String update,
			String delete) {
		this.findAll = findAll;
		this.findById = findById;
		this.update = update;
		this.delete = delete;
		this.rowMapper = rowMapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<E> findAll() {
		return jdbcTemplate.query(findAll, rowMapper);		
	}

	public E findById(ID id) {
		return jdbcTemplate.queryForObject(findById, rowMapper, id);
	}

	public void delete(ID id) {
		jdbcTemplate.update(delete, id);	
	}

	public E update(E entity) {
		jdbcTemplate.update(update, setValuesForUpdate(entity));
		return findById(getId(entity));
	}

	public abstract E save(E entity);

	public abstract PreparedStatementSetter setValuesForUpdate(E entity);
	
	public abstract ID getId(E entity);

	protected List<E> findByQuery(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
	}

	protected void executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) {
		jdbcTemplate.update(sql, preparedStatementSetter);
	}
}