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
import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

@Component
public class StudentDaoImpl extends AbstractDao<Student, Long> implements StudentDao {
	
	private static final String FIND_ALL = "SELECT s.student_id, "
			+ "s.student_person_id, "
			+ "s.student_group_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "g.group_name "
			+ "FROM public.students s, public.groups g, public.persons p "
			+ "WHERE s.student_person_id = p.person_id AND s.student_group_id = g.group_id;";
	private static final String FIND_BY_ID = "SELECT s.student_id,"
			+ "s.student_person_id, "
			+ "s.student_group_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "g.group_name "
			+ "FROM public.students s, public.groups g, public.persons p "
			+ "WHERE s.student_person_id = p.person_id AND s.student_group_id = g.group_id AND s.student_id = ?;";
	private static final String SAVE_PERSON = "INSERT INTO public.persons(person_first_name, person_last_name) VALUES (?, ?);";
	private static final String SAVE_STUDENT = "INSERT INTO public.students(student_person_id, student_group_id) VALUES (?, ?);";
	private static final String UPDATE_PERSON = "UPDATE public.persons "
			+ "SET person_first_name=?, person_last_name=? "
			+ "WHERE person_id=?;";
	private static final String UPDATE_STUDENT = "UPDATE public.students "
			+ "SET student_group_id=? "
			+ "WHERE student_id=?;";
	private static final String DELETE = "DELETE FROM public.persons "
			+ "WHERE person_id = ("
			+ "SELECT student_person_id "
			+ "FROM public.students "
			+ "WHERE student_id = ?);";
	private static final String FIND_BY_GROUP_ID = "SELECT s.student_id,"
			+ "s.student_person_id, "
			+ "s.student_group_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "g.group_name "
			+ "FROM public.students s, public.groups g, public.persons p "
			+ "WHERE s.student_person_id = p.person_id AND s.student_group_id = g.group_id AND s.student_group_id = ?;";
	private static final String PERSON_ID_KEY = "person_id";
	private static final String STUDENT_ID_KEY = "student_id";
	
	private final JdbcTemplate jdbcTemplate;
	
	public StudentDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Student> rowMapper) {		
		super(jdbcTemplate, 
				rowMapper,
				FIND_ALL, 
				FIND_BY_ID,
				DELETE);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Student> findByGroupId(Long groupId) throws DaoNotFoundException {
		
		PreparedStatementSetter preparedStatementSetter = ps -> {
			ps.setLong(1, groupId);
		};
		
		return findByQuery(FIND_BY_GROUP_ID, preparedStatementSetter);
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForSave(Student student) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE_PERSON, new String[] {PERSON_ID_KEY});
	                ps.setString(1, student.getFirstName());
	                ps.setString(2, student.getLastName());
	                return ps;
	              }, keyHolder);
	    
		Long personId = keyHolder.getKey().longValue();
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE_STUDENT, new String[] {STUDENT_ID_KEY});
	                ps.setLong(1, personId);
	                ps.setLong(2, student.getGroup().getId());
	                return ps;
	              };
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForUpdate(Student student) {
		
		executeUpdate(UPDATE_PERSON, ps -> {
			ps.setString(1, student.getFirstName());
	        ps.setString(2, student.getLastName());
	        ps.setLong(3, student.getPersonId());
		});
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(UPDATE_STUDENT, new String[] {STUDENT_ID_KEY});	                
	                ps.setLong(1, student.getGroup().getId());
	                ps.setLong(2, student.getStudentId());
	                return ps;
	              };
	}
}
