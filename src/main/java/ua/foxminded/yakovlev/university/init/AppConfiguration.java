package ua.foxminded.yakovlev.university.init;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.dao.PositionDao;
import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.dao.impl.CourseDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.GroupDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.LecturerDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.PositionDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.StudentDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.TimetableRecordDaoImpl;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.mapper.PositionMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;

@Configuration
public class AppConfiguration {

	private static final String DB_PROPERTIES = "university_db";
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";

	@Bean (name="jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() {

		ResourceBundle dbResourceBundle = ResourceBundle.getBundle(DB_PROPERTIES);

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(dbResourceBundle.getString(URL),
				dbResourceBundle.getString(USER), dbResourceBundle.getString(PASSWORD));
		driverManagerDataSource.setDriverClassName(dbResourceBundle.getString(DRIVER));
		
		return new JdbcTemplate(driverManagerDataSource);
	}
	
	@Bean (name="courseDao")
	@Autowired
	public CourseDao getCourseDao(JdbcTemplate jdbcTemplate, CourseMapper courseMapper) {		
		return new CourseDaoImpl(jdbcTemplate, courseMapper);
	}
	
	@Bean (name="groupDao")
	@Autowired
	public GroupDao getCourseDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {		
		return new GroupDaoImpl(jdbcTemplate, groupMapper);
	}
	
	@Bean (name="positionDao")
	@Autowired
	public PositionDao getCourseDao(JdbcTemplate jdbcTemplate, PositionMapper positionMapper) {		
		return new PositionDaoImpl(jdbcTemplate, positionMapper);
	}
	
	@Bean (name="lecturerDao")
	@Autowired
	public LecturerDao getCourseDao(JdbcTemplate jdbcTemplate, LecturerMapper lecturerMapper) {		
		return new LecturerDaoImpl(jdbcTemplate, lecturerMapper);
	}
	
	@Bean (name="studentDao")
	@Autowired
	public StudentDao getCourseDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {		
		return new StudentDaoImpl(jdbcTemplate, studentMapper);
	}
	
	@Bean (name="timetableRecordDao")
	@Autowired
	public TimetableRecordDao getCourseDao(JdbcTemplate jdbcTemplate, TimetableRecordMapper timetableRecordMapper) {		
		return new TimetableRecordDaoImpl(jdbcTemplate, timetableRecordMapper);
	}
}
