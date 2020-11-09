package ua.foxminded.yakovlev.university.dao.impl;

import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.PositionDao;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

@Component
public class PositionDaoImpl extends AbstractDao<Position, Long> implements PositionDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PositionDaoImpl.class);

	private static final String FIND_ALL = "SELECT * FROM public.positions;";
	private static final String SAVE = "INSERT INTO public.positions(position_name) VALUES (?);";
	private static final String FIND_BY_ID = "SELECT * FROM public.positions WHERE position_id = ?;";
	private static final String UPDATE = "UPDATE public.positions "
			+ "SET position_name=? "
			+ "WHERE position_id=?;";
	private static final String DELETE = "DELETE FROM public.positions WHERE position_id = ?;";
	private static final String FIND_BY_POSITION_NAME = "SELECT * FROM public.positions WHERE position_name = ?;";
	private static final String ID_KEY = "position_id";
	
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Position> positionMapper;
	
	public PositionDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Position> positionMapper) {
		super(jdbcTemplate, positionMapper, FIND_ALL, FIND_BY_ID, DELETE);
		this.jdbcTemplate = jdbcTemplate;
		this.positionMapper = positionMapper;
	}

	@Override
	public Position findPositionByName(String positionName) throws DaoNotFoundException {
		
		try {
			return jdbcTemplate.queryForObject(FIND_BY_POSITION_NAME, positionMapper, positionName);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Entity is not found", e);
			throw new DaoNotFoundException("Entity is not found");
		}
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForSave(Position position) {
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE, new String[] { ID_KEY });
	                ps.setString(1, position.getName());
	                return ps;
	              };
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForUpdate(Position position) {
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(UPDATE, new String[] { ID_KEY });
	                ps.setString(1, position.getName());
	                ps.setLong(2, position.getId());
	                return ps;
	              };
	}
}
