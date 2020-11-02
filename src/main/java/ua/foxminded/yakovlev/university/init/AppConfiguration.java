package ua.foxminded.yakovlev.university.init;

import org.springframework.beans.factory.annotation.Value;
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
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.GroupServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.LecturerServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.PositionServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.StudentServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.TimetableRecordServiceImpl;
import ua.foxminded.yakovlev.university.validator.GroupValidator;
import ua.foxminded.yakovlev.university.validator.LecturerValidator;
import ua.foxminded.yakovlev.university.validator.PositionValidator;
import ua.foxminded.yakovlev.university.validator.StudentValidator;
import ua.foxminded.yakovlev.university.validator.TimetableRecordValidator;

@Configuration
public class AppConfiguration {

	@Value("${driver}")
	private String driver;
	@Value("${url}")
	private String url;
	@Value("${user}")
	private String user;
	@Value("${password}")
	private String password;

	@Bean (name="jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() {

		DriverManagerDataSource driverManagerDataSource = 
				new DriverManagerDataSource(url, user, password);
		driverManagerDataSource.setDriverClassName(driver);
		
		return new JdbcTemplate(driverManagerDataSource);
	}
	
	@Bean (name="courseDao")
	public CourseDao getCourseDao(JdbcTemplate jdbcTemplate, CourseMapper courseMapper) {		
		return new CourseDaoImpl(jdbcTemplate, courseMapper);
	}
	
	@Bean (name="groupDao")
	public GroupDao getGroupDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {		
		return new GroupDaoImpl(jdbcTemplate, groupMapper);
	}
	
	@Bean (name="positionDao")
	public PositionDao getPositionDao(JdbcTemplate jdbcTemplate, PositionMapper positionMapper) {		
		return new PositionDaoImpl(jdbcTemplate, positionMapper);
	}
	
	@Bean (name="lecturerDao")
	public LecturerDao getLecturerDao(JdbcTemplate jdbcTemplate, LecturerMapper lecturerMapper) {		
		return new LecturerDaoImpl(jdbcTemplate, lecturerMapper);
	}
	
	@Bean (name="studentDao")
	public StudentDao getStudentDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {		
		return new StudentDaoImpl(jdbcTemplate, studentMapper);
	}
	
	@Bean (name="timetableRecordDao")
	public TimetableRecordDao getTimetableRecordDao(JdbcTemplate jdbcTemplate, 
			TimetableRecordMapper timetableRecordMapper,
			GroupMapper groupMapper) {		
		return new TimetableRecordDaoImpl(jdbcTemplate, timetableRecordMapper, groupMapper);
	}
	
	@Bean (name="groupService")
	public GroupService getCourseService(GroupDao groupDao, GroupValidator validator) {		
		return new GroupServiceImpl(groupDao, validator);
	}
	
	@Bean (name="positionService")
	public PositionService getPositionService(PositionDao positionDao, PositionValidator validator) {		
		return new PositionServiceImpl(positionDao, validator);
	}
	
	@Bean (name="studentService")
	public StudentService getStudentService(StudentDao studentDao, StudentValidator validator) {		
		return new StudentServiceImpl(studentDao, validator);
	}
	
	@Bean (name="lecturerService")
	public LecturerService getLecturerService(LecturerDao lecturerDao, LecturerValidator validator) {		
		return new LecturerServiceImpl(lecturerDao, validator);
	}
	
	@Bean (name="timetableRecordService")
	public TimetableRecordService getTimetableRecordService(TimetableRecordDao timetableRecordDao, TimetableRecordValidator validator) {		
		return new TimetableRecordServiceImpl(timetableRecordDao, validator);
	}
}
