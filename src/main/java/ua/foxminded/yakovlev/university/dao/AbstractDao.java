package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ua.foxminded.yakovlev.university.exception.AlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ConstrainException;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.exception.CantUpdateException;

public abstract class AbstractDao<E, ID> {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

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
		
		try {
			return jdbcTemplate.queryForObject(findById, rowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Entity not found", e);
			throw new NotFoundException("Entity not found");
		}
	}

	public void delete(ID id) {
		
		try {
			jdbcTemplate.update(delete, id);
		} catch (DataIntegrityViolationException e) {
			logger.error("There is a constrain preventing deletion", e);
			throw new ConstrainException("There is a constrain preventing deletion");
		}
	}
	
	@SuppressWarnings("unchecked")
	public E update(E entity) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(getPreparedStatementCreatorForUpdate(entity), keyHolder);
		} catch (DuplicateKeyException e) {
			logger.error("entity already exists", e);
			throw new AlreadyExistsException("entity already exists");
		}
		
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}
	
	@SuppressWarnings("unchecked")
	public E save(E entity) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(getPreparedStatementCreatorForSave(entity), keyHolder);
		} catch (DuplicateKeyException e) {
			logger.error("entity already exists", e);
			throw new AlreadyExistsException("entity already exists");
		}
		
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}

	public abstract PreparedStatementCreator getPreparedStatementCreatorForUpdate(E entity);
	
	public abstract PreparedStatementCreator getPreparedStatementCreatorForSave(E entity);

	protected List<E> findByQuery(String sql, PreparedStatementSetter preparedStatementSetter) {		
		
		try {
			return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Entity not found", e);
			throw new NotFoundException("Entity not found");
		}
	}

	protected void executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) {		
		try {
		jdbcTemplate.update(sql, preparedStatementSetter);
		} catch (Exception e) {
			logger.error("Update fail", e);
			throw new CantUpdateException("Update fail", e);
		}
	}
}