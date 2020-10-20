package ua.foxminded.yakovlev.university.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractDao<E, ID> implements EntityDao<E, ID> {

	private final String findAll;
	private final String save;
	private final String findById;
	private final String update;
	private final String delete;
	private final RowMapper<E> rowMapper;
	
	private final JdbcTemplate jdbcTemplate;

	public AbstractDao(JdbcTemplate jdbcTemplate, RowMapper<E> rowMapper, String findAll, String save, String findById, String update,
			String delete) {
		this.findAll = findAll;
		this.save = save;
		this.findById = findById;
		this.update = update;
		this.delete = delete;
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
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

	public void update(E entity) {
		jdbcTemplate.update(update, setValuesForUpdate(entity));
	}

	public void save(E entity) {
		jdbcTemplate.update(save, setValuesForSave(entity));
	}

	public abstract PreparedStatementSetter setValuesForUpdate(E entity);

	public abstract PreparedStatementSetter setValuesForSave(E entity);

	protected List<E> findByQuery(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
	}

	protected void executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) {
		jdbcTemplate.update(sql, preparedStatementSetter);
	}
}