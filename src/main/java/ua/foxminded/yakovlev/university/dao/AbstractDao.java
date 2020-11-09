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

import ua.foxminded.yakovlev.university.exception.DaoAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.DaoConstrainException;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.DaoRuntimeException;

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

	public E findById(ID id) throws DaoNotFoundException {
		
		try {
			return jdbcTemplate.queryForObject(findById, rowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Entity not found", e);
			throw new DaoNotFoundException("Entity not found");
		}
	}

	public void delete(ID id) throws DaoConstrainException {
		
		try {
			jdbcTemplate.update(delete, id);
		} catch (DataIntegrityViolationException e) {
			logger.warn("There is a constrain preventing deletion", e);
			throw new DaoConstrainException("There is a constrain preventing deletion");
		}
	}
	
	@SuppressWarnings("unchecked")
	public E update(E entity) throws DaoNotFoundException, DaoAlreadyExistsException {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(getPreparedStatementCreatorForUpdate(entity), keyHolder);
		} catch (DuplicateKeyException e) {
			logger.warn("entity already exists", e);
			throw new DaoAlreadyExistsException("entity already exists");
		}
		
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}
	
	@SuppressWarnings("unchecked")
	public E save(E entity) throws DaoNotFoundException, DaoAlreadyExistsException, DaoConstrainException {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			jdbcTemplate.update(getPreparedStatementCreatorForSave(entity), keyHolder);
		} catch (DuplicateKeyException e) {
			logger.warn("entity already exists", e);
			throw new DaoAlreadyExistsException("entity already exists");
		}
		
	    ID entityId = (ID) keyHolder.getKey();
	    
		return findById(entityId);
	}

	public abstract PreparedStatementCreator getPreparedStatementCreatorForUpdate(E entity);
	
	public abstract PreparedStatementCreator getPreparedStatementCreatorForSave(E entity);

	protected List<E> findByQuery(String sql, PreparedStatementSetter preparedStatementSetter) throws DaoNotFoundException {		
		
		try {
			return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Entity not found", e);
			throw new DaoNotFoundException("Entity not found");
		}
	}

	protected void executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) {		
		try {
		jdbcTemplate.update(sql, preparedStatementSetter);
		} catch (Exception e) {
			logger.warn("Update fail", e);
			throw new DaoRuntimeException("Update fail", e);
		}
	}
}