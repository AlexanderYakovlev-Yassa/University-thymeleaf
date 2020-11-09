package ua.foxminded.yakovlev.university.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

@Component
public class LecturerDaoImpl extends AbstractDao<Lecturer, Long> implements LecturerDao {
	
	private static final String FIND_ALL = "SELECT l.lecturer_id, "
			+ "l.lecturer_person_id, "
			+ "l.lecturer_position_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "g.position_name "
			+ "FROM public.lecturers l, public.positions g, public.persons p "
			+ "WHERE l.lecturer_person_id = p.person_id AND l.lecturer_position_id = g.position_id;";
	private static final String FIND_BY_ID = "SELECT l.lecturer_id,"
			+ "l.lecturer_person_id, "
			+ "l.lecturer_position_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "ps.position_name "
			+ "FROM public.lecturers l, public.positions ps, public.persons p "
			+ "WHERE l.lecturer_person_id = p.person_id AND l.lecturer_position_id = ps.position_id AND l.lecturer_id = ?;";
	private static final String SAVE_PERSON = "INSERT INTO public.persons(person_first_name, person_last_name) VALUES (?, ?);";
	private static final String SAVE_LECTURER = "INSERT INTO public.lecturers(lecturer_person_id, lecturer_position_id) VALUES (?, ?);";
	private static final String UPDATE_PERSON = "UPDATE public.persons "
			+ "SET person_first_name=?, person_last_name=? "
			+ "WHERE person_id=?;";
	private static final String UPDATE_LECTURER = "UPDATE public.lecturers "
			+ "SET lecturer_position_id=? "
			+ "WHERE lecturer_id=?;";
	private static final String DELETE = "DELETE FROM public.persons "
			+ "WHERE person_id = ("
			+ "SELECT lecturer_person_id "
			+ "FROM public.lecturers "
			+ "WHERE lecturer_id = ?);";
	private static final String FIND_BY_POSITION_ID = "SELECT l.lecturer_id,"
			+ "l.lecturer_person_id, "
			+ "l.lecturer_position_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "ps.position_name "
			+ "FROM public.lecturers l, public.positions ps, public.persons p "
			+ "WHERE l.lecturer_person_id = p.person_id AND l.lecturer_position_id = ps.position_id AND l.lecturer_position_id = ?;";
	private static final String PERSON_ID_KEY = "person_id";
	private static final String LECTURER_ID_KEY = "lecturer_id";
	
	private final JdbcTemplate jdbcTemplate;
	
	public LecturerDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Lecturer> rowMapper) {		
		super(jdbcTemplate, 
				rowMapper,
				FIND_ALL, 
				FIND_BY_ID,
				DELETE);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Lecturer> findByPositionId(Long positionId) throws DaoNotFoundException {
		
		PreparedStatementSetter preparedStatementSetter = ps -> {
			ps.setLong(1, positionId);
		};
		
		return findByQuery(FIND_BY_POSITION_ID, preparedStatementSetter);
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForSave(Lecturer lecturer) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE_PERSON, new String[] {PERSON_ID_KEY});
	                ps.setString(1, lecturer.getFirstName());
	                ps.setString(2, lecturer.getLastName());
	                return ps;
	              }, keyHolder);
	    
		Long personId = keyHolder.getKey().longValue();
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE_LECTURER, new String[] {LECTURER_ID_KEY});
	                ps.setLong(1, personId);
	                ps.setLong(2, lecturer.getPosition().getId());
	                return ps;
	              };
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForUpdate(Lecturer lecturer) {
		
		executeUpdate(UPDATE_PERSON, ps -> {
			ps.setString(1, lecturer.getFirstName());
	        ps.setString(2, lecturer.getLastName());
	        ps.setLong(3, lecturer.getPersonId());
		});
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(UPDATE_LECTURER, new String[] {LECTURER_ID_KEY});	                
	                ps.setLong(1, lecturer.getPosition().getId());
	                ps.setLong(2, lecturer.getLecturerId());
	                return ps;
	              };
	}	
}
