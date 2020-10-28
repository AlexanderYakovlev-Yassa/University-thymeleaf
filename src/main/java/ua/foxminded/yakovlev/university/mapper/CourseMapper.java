package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.entity.Course;

public class CourseMapper implements RowMapper<Course> {
	
	private static final String COURSE_ID = "course_id";
	private static final String COURSE_NAME = "course_name";
	private static final String COURSE_DESCRIPTION = "course_description";

	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Course course = new Course();
		
		course.setId(rs.getLong(COURSE_ID));
		course.setName(rs.getString(COURSE_NAME));
		course.setDescription(rs.getString(COURSE_DESCRIPTION));
				
		return course;
	}
}
