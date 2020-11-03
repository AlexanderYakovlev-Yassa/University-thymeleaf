package ua.foxminded.yakovlev.university.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import ua.foxminded.yakovlev.university.dao.impl.ScriptExecutor;
import ua.foxminded.yakovlev.university.dao.impl.StudentDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.TimetableRecordDaoImpl;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.mapper.PositionMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.CourseServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.GroupServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.LecturerServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.PositionServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.StudentServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.TimetableRecordServiceImpl;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;
import ua.foxminded.yakovlev.university.util.FileReader;
@Configuration
@ComponentScan("ua.foxminded.yakovlev.university")
@PropertySource("university_db.properties")
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
	
	@Bean (name="scriptExecutor")
	public ScriptExecutor getScriptExecutor(JdbcTemplate jdbcTemplate) {
		return new ScriptExecutor(jdbcTemplate);
	}
	
	@Bean (name="databaseGenerator")
	public TestDatabaseGenerator getDatabaseGenerator(FileReader fileReader, ScriptExecutor scriptExecutor) {
		return new TestDatabaseGenerator(fileReader, scriptExecutor);
	}
	
	@Bean (name="courseService")
	public CourseService getCourseService(CourseDao courseDao) {		
		return new CourseServiceImpl(courseDao);
	}
	
	@Bean (name="groupService")
	public GroupService getGroupService(GroupDao groupDao) {		
		return new GroupServiceImpl(groupDao);
	}
	
	@Bean (name="positionService")
	public PositionService getPositionService(PositionDao positionDao) {		
		return new PositionServiceImpl(positionDao);
	}
	
	@Bean (name="studentService")
	public StudentService getStudentService(StudentDao studentDao) {		
		return new StudentServiceImpl(studentDao);
	}
	
	@Bean (name="lecturerService")
	public LecturerService getLecturerService(LecturerDao lecturerDao) {		
		return new LecturerServiceImpl(lecturerDao);
	}
	
	@Bean (name="timetableRecordService")
	public TimetableRecordService getTimetableRecordService(TimetableRecordDao timetableRecordDao) {		
		return new TimetableRecordServiceImpl(timetableRecordDao);
	}
}
