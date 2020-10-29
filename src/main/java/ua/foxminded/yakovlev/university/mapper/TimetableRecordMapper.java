package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@Component
public class TimetableRecordMapper implements RowMapper<TimetableRecord> {
		
	private static final String TIMETABLE_RECORD_ID = "timetable_record_id";
	private static final String TIMETABLE_RECORD_TIME = "timetable_record_time";
	private static final String TIMETABLE_RECORD_LECTURER_ID = "timetable_record_lecturer_id";
	private static final String TIMETABLE_RECORD_COURSE_ID = "timetable_record_course_id";
	
	private static final String PERSON_ID = "lecturer_person_id";
	private static final String FIRST_NAME = "person_first_name";
	private static final String LAST_NAME = "person_last_name";
	private static final String POSITION_ID = "lecturer_position_id";
	private static final String POSITION_NAME = "position_name";
	
	private static final String COURSE_NAME = "course_name";
	private static final String COURSE_DESCRIPTION = "course_description";
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";//"2007-12-03 10:15:30"

	@Override
	public TimetableRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		
		TimetableRecord timetableRecord = new TimetableRecord();
		Lecturer lecturer = new Lecturer();
		Position position = new Position();
		Course course = new Course();
		
		position.setId(rs.getLong(POSITION_ID));
		position.setName(rs.getString(POSITION_NAME));
		
		lecturer.setPersonId(rs.getLong(PERSON_ID));
		lecturer.setLecturerId(rs.getLong(TIMETABLE_RECORD_LECTURER_ID));
		lecturer.setFirstName(rs.getString(FIRST_NAME));
		lecturer.setLastName(rs.getString(LAST_NAME));
		lecturer.setPosition(position);
		
		course.setId(rs.getLong(TIMETABLE_RECORD_COURSE_ID));
		course.setName(rs.getString(COURSE_NAME));
		course.setDescription(rs.getString(COURSE_DESCRIPTION));
		
		timetableRecord.setId(rs.getLong(TIMETABLE_RECORD_ID));
		timetableRecord.setDate(LocalDateTime.parse(rs.getString(TIMETABLE_RECORD_TIME), formatter));
		timetableRecord.setLecturer(lecturer);
		timetableRecord.setCourse(course);
		timetableRecord.setGroupList(new ArrayList<>());
		
		return timetableRecord;
	}
}
