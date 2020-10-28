package ua.foxminded.yakovlev.university.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class AbstractDao<E, ID> {

	private final String findAll;
	private final String findById;
	private final String delete;
	private final RowMapper<E> rowMapper;
	private final JdbcTemplate jdbcTemplate;

	public AbstractDao(JdbcTemplate jdbcTemplate, 
			RowMapper<E> rowMapper, 
			String findAll, 
			String findById,
			String delete) {
		this.findAll = findAll;
		this.findById = findById;
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
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(getPreparedStatementCreatorForUpdate(entity), keyHolder);
	    @SuppressWarnings("unchecked")
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}

	public E save(E entity) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(getPreparedStatementCreatorForSave(entity), keyHolder);
	    @SuppressWarnings("unchecked")
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}

	public abstract PreparedStatementCreator getPreparedStatementCreatorForUpdate(E entity);
	
	public abstract PreparedStatementCreator getPreparedStatementCreatorForSave(E entity);

	protected List<E> findByQuery(String sql, PreparedStatementSetter preparedStatementSetter) {
		return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
	}

	protected void executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) {
		jdbcTemplate.update(sql, preparedStatementSetter);
	}
}