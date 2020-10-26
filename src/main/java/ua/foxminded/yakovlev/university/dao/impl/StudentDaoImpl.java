package ua.foxminded.yakovlev.university.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.entity.Student;

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
	private static final String UPDATE = "UPDATE public.persons "
			+ "SET person_first_name=?, person_last_name=? "
			+ "WHERE person_id=?; "
			+ "UPDATE public.students "
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
	
	private final JdbcTemplate jdbcTemplate;
	
	public StudentDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Student> rowMapper) {		
		super(jdbcTemplate, 
				rowMapper,
				FIND_ALL, 
				FIND_BY_ID, 
				UPDATE, 
				DELETE);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Student> findByGroupId(Long groupId) {
		
		PreparedStatementSetter preparedStatementSetter = ps -> {
			ps.setLong(1, groupId);
		};
		
		return findByQuery(FIND_BY_GROUP_ID, preparedStatementSetter);
	}

	@Override
	public PreparedStatementSetter setValuesForUpdate(Student student) {
		
		return ps -> {
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getLastName());
			ps.setLong(3, student.getPersonId());
			ps.setLong(4, student.getGroup().getId());
			ps.setLong(5,  student.getStudentId());
		};
	}
	
	@Override
	public Student save(Student student) {
		
		Long personId = jdbcTemplate.query("INSERT INTO public.persons(person_first_name, person_last_name) VALUES (?, ?) RETURNING person_id;", 
				ps -> {
					ps.setString(1, student.getFirstName());
					ps.setString(2, student.getLastName());
				},
				rs -> {
					rs.next();
					return rs.getLong("person_id");
				});
		
		Long studentId = jdbcTemplate.query("INSERT INTO public.students(student_person_id, student_group_id) VALUES (?, ?) RETURNING student_id;",
				ps -> {
					ps.setLong(1, personId);
					ps.setLong(2, student.getGroup().getId());
				},
				rs -> {
					rs.next();
					return rs.getLong("student_id");
				});
		
		return findById(studentId);
	}

	@Override
	public Long getId(Student student) {		
		return student.getStudentId();
	}
}
