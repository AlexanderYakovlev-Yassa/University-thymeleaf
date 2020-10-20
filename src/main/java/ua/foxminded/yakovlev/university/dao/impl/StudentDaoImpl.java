package ua.foxminded.yakovlev.university.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;

public class StudentDaoImpl extends AbstractDao<Student, Integer> implements StudentDao {
	
	private static final String FIND_ALL = "SELECT s.student_id, "
			+ "s.student_person_id, "
			+ "s.student_group_id, "
			+ "p.person_first_name, "
			+ "p.person_last_name, "
			+ "g.group_name "
			+ "FROM public.students s, public.groups g, public.persons p "
			+ "WHERE s.student_person_id = p.person_id AND s.student_group_id = g.group_id;";
	private static final String SAVE = "INSERT INTO public.persons( "
			+ "person_first_name, person_last_name) "
			+ "VALUES (?, ?); "
			+ "INSERT INTO public.students( "
			+ "student_person_id, student_group_id) "
			+ "VALUES ( "
			+ "(SELECT MAX(person_id) "
			+ "FROM public.persons "
			+ "WHERE person_first_name = ? AND person_last_name = ? "
			+ "GROUP BY person_first_name "
			+ "), ?);";
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
	private static final String DELETE = "DELETE FROM public.students WHERE id=?; ";
	
	public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, new StudentMapper(), FIND_ALL, SAVE, FIND_BY_ID, UPDATE, DELETE);
	}

	@Override
	public List<Student> findByGroupId(Integer groupId) {
		return null;
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
	public PreparedStatementSetter setValuesForSave(Student student) {
		
		return ps -> {
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getFirstName());
			ps.setString(4, student.getLastName());
			ps.setLong(5, student.getGroup().getId());
		};
	}
}
