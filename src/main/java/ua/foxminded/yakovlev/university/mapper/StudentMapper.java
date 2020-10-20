package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Student;

public class StudentMapper implements RowMapper<Student> {
	
	private static final String PERSON_ID = "student_person_id";
	private static final String FIRST_NAME = "person_first_name";
	private static final String LAST_NAME = "person_last_name";
	private static final String STUDENT_ID = "student_id";
	private static final String GROUP_ID = "student_group_id";
	private static final String GROUP_NAME = "group_name";

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Student student = new Student();
		Group group = new Group();
		
		group.setId(rs.getLong(GROUP_ID));
		group.setName(rs.getString(GROUP_NAME));
		student.setPersonId(rs.getLong(PERSON_ID));
		student.setStudentId(rs.getLong(STUDENT_ID));
		student.setFirstName(rs.getString(FIRST_NAME));
		student.setLastName(rs.getString(LAST_NAME));
		student.setGroup(group);
		
		return student;
	}
}
