package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;

public class LecturerMapper implements RowMapper<Lecturer> {
	
	private static final String PERSON_ID = "lecturer_person_id";
	private static final String FIRST_NAME = "person_first_name";
	private static final String LAST_NAME = "person_last_name";
	private static final String LECTURER_ID = "lecturer_id";
	private static final String POSITION_ID = "lecturer_position_id";
	private static final String POSITION_NAME = "position_name";

	@Override
	public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Lecturer lecturer = new Lecturer();
		Position position = new Position();
		
		position.setId(rs.getLong(POSITION_ID));
		position.setName(rs.getString(POSITION_NAME));
		lecturer.setPersonId(rs.getLong(PERSON_ID));
		lecturer.setLecturerId(rs.getLong(LECTURER_ID));
		lecturer.setFirstName(rs.getString(FIRST_NAME));
		lecturer.setLastName(rs.getString(LAST_NAME));
		lecturer.setPosition(position);
		
		return lecturer;
	}
}
